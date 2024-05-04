package nl.robinthedev.tictactoe.games;

import nl.robinthedev.tictactoe.game.events.GameDraw;
import nl.robinthedev.tictactoe.game.events.GameFinished;
import nl.robinthedev.tictactoe.game.events.NewGameStarted;
import nl.robinthedev.tictactoe.game.events.SquareMarked;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("GamesEventHandler")
class GamesEventHandler {

  private static final Logger log = LoggerFactory.getLogger(GamesEventHandler.class);
  private final Games games;

  public GamesEventHandler(Games games) {
    this.games = games;
    log.info("Started GameEventHandler");
  }

  @EventHandler
  void handle(NewGameStarted event) {
    log.info("{}", event);
    games.gameStarted(Game.of(event));
  }

  @EventHandler
  void handle(SquareMarked event) {
    log.info("{}", event);
    Game game =
        games
            .get(event.gameId())
            .updateGrid(event.gridState())
            .changeCurrentPlayer(event.nextPlayer());
    games.update(game);
  }

  @EventHandler
  void handle(GameEndedInDraw event) {
    log.info("{}", event);
    games.remove(event.gameId());
  }

  @EventHandler
  void handle(GameFinished event) {
    log.info("{}", event);
    games.remove(event.gameId());
  }
}
