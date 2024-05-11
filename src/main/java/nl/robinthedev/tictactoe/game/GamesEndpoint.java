package nl.robinthedev.tictactoe.game;

import java.util.UUID;
import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.game.api.SquareToMark;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GamesEndpoint {

  private static final PlayerId onlyHuman =
      new PlayerId(UUID.fromString("1738158c-9d8f-4343-9203-9a081cee4a0f"));
  private static final PlayerId botAnnabel =
      new PlayerId(UUID.fromString("2be1d7ed-2f87-4b9f-9b41-e777b57dca6a"));

  private final GameCommands gameCommands;

  public GamesEndpoint(GameCommands gameCommands) {
    this.gameCommands = gameCommands;
  }

  @PostMapping("games/new")
  public void createGame() {
    gameCommands.startNewGame(onlyHuman, botAnnabel);
  }

  @PostMapping("games/{gameId}/mark")
  public void createGame(@PathVariable UUID gameId, @RequestBody SquareToMark squareToMark) {
    gameCommands.markSquare(new GameId(gameId), onlyHuman, squareToMark);
  }
}
