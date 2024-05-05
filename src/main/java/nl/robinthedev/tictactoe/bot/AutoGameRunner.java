package nl.robinthedev.tictactoe.bot;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.NewGridState;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.game.api.SquareSymbol;
import nl.robinthedev.tictactoe.game.api.commands.MarkSquare;
import nl.robinthedev.tictactoe.game.api.commands.StartNewGame;
import nl.robinthedev.tictactoe.lobby.api.Grid;
import nl.robinthedev.tictactoe.lobby.api.RunningGames;
import nl.robinthedev.tictactoe.lobby.api.queries.FetchRunningGames;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(matchIfMissing = true, value = "bots.disabled", havingValue = "false")
class AutoGameRunner {

  private static final Logger log = LoggerFactory.getLogger(AutoGameRunner.class);
  public static final int CONCURRENT_GAMES_PER_BOT = 3;

  private final Bots bots;
  private final QueryGateway queries;
  private final CommandGateway commands;

  public AutoGameRunner(Bots bots, QueryGateway queries, CommandGateway commands) {
    this.bots = bots;
    this.queries = queries;
    this.commands = commands;
    log.info("Started AutoGameRunner");
  }

  @Scheduled(initialDelay = 5_000, fixedDelay = 1_000)
  void work() {
    bots.allBots().forEach(this::advanceBot);
  }

  private void advanceBot(Bot bot) {
    RunningGames runningGames = getRunningGames(bot.getPlayerId());
    if (!runningGames.games().isEmpty()) {
      log.info("Advancing running games");
      advanceRunningGames(runningGames);
    }

    if (runningGames.games().size() < CONCURRENT_GAMES_PER_BOT) {
      createNewGame(bot.getPlayerId());
    }
  }

  private void createNewGame(PlayerId playerX) {
    var playerO = bots.anyOtherBot(playerX);
    var command = new StartNewGame(new GameId(UUID.randomUUID()), playerX, playerO);
    commands.send(command);
  }

  private void advanceRunningGames(RunningGames running) {
    log.info("Found {}.", running);
    running.games().stream()
        .filter(game -> game.currentPlayer().equals(running.playerId()))
        .forEach(
            game -> {
              var bot =
                  bots.findOrCreate(
                      game.currentPlayer(), game.currentPlayer().toString().substring(6));
              var move = bot.makeMove(game.gameId(), toNewGridState(game.grid()));
              commands.send(
                  new MarkSquare(game.gameId(), game.currentPlayer(), move.squareToMark()));
            });
  }

  private NewGridState toNewGridState(Grid grid) {
    return new NewGridState(
        grid.cells().stream()
            .map(
                mark ->
                    switch (mark) {
                      case X -> SquareSymbol.X;
                      case O -> SquareSymbol.O;
                      case EMPTY -> SquareSymbol.EMPTY;
                    })
            .toList());
  }

  private RunningGames getRunningGames(PlayerId playerId) {
    try {
      return queries.query(new FetchRunningGames(playerId), RunningGames.class).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}
