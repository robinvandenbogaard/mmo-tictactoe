package nl.robinthedev.tictactoe.bot;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import nl.robinthedev.tictactoe.account.api.AccountId;
import nl.robinthedev.tictactoe.account.api.Username;
import nl.robinthedev.tictactoe.botaccount.commands.CreateBotAccount;
import nl.robinthedev.tictactoe.game.api.NewGridState;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.game.api.SquareSymbol;
import nl.robinthedev.tictactoe.game.api.commands.MarkSquare;
import nl.robinthedev.tictactoe.lobby.api.Grid;
import nl.robinthedev.tictactoe.lobby.api.RunningGames;
import nl.robinthedev.tictactoe.lobby.api.queries.FetchRunningGames;
import nl.robinthedev.tictactoe.matchmaker.QueueResult;
import nl.robinthedev.tictactoe.matchmaker.RequestGame;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(matchIfMissing = true, value = "bots.disabled", havingValue = "false")
class AutoGameRunner implements ApplicationListener<ApplicationStartedEvent> {

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
      log.debug("Advancing running games");
      advanceRunningGames(runningGames);
    }

    if (runningGames.games().size() < CONCURRENT_GAMES_PER_BOT) {
      requestNewGame(bot.getPlayerId());
    }
  }

  private void requestNewGame(PlayerId playerX) {
    try {
      queries.query(new RequestGame(new AccountId(playerX.id())), QueueResult.class).get();
    } catch (InterruptedException | ExecutionException e) {
      log.error("Couldn't queue: ", e);
    }
  }

  private void advanceRunningGames(RunningGames running) {
    log.debug("Found {} games: {}.", running.games().size(), running);
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

  @Override
  public void onApplicationEvent(ApplicationStartedEvent ignore) {
    createBotCommand("df78433d-72b0-4a9d-83b3-ef5aa021443b", "TicTacTech")
        .ifPresent(commands::send);
    createBotCommand("2be1d7ed-2f87-4b9f-9b41-e777b57dca6a", "TacTicTony")
        .ifPresent(commands::send);
    createBotCommand("a9dfd8b2-dd64-441b-aa30-749d7b0124b8", "TicTacTactician")
        .ifPresent(commands::send);
    createBotCommand("a2315108-0423-4ef5-9b92-8261104e3022", "TicTacTuring")
        .ifPresent(commands::send);
    createBotCommand("d97d7862-34d3-4946-afae-d9fe419fcc23", "TicTacThinker")
        .ifPresent(commands::send);
  }

  private Optional<CreateBotAccount> createBotCommand(String uuid, String John) {
    var botUuid = UUID.fromString(uuid);
    if (bots.doesNotExist(new PlayerId(botUuid)))
      return Optional.of(new CreateBotAccount(new AccountId(botUuid), new Username(John)));
    return Optional.empty();
  }
}
