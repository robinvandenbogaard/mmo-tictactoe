package nl.robinthedev.tictactoe.game;

import java.util.UUID;
import nl.robinthedev.tictactoe.account.api.AccountId;
import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.game.api.SquareToMark;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GamesEndpoint {

  private final GameCommands gameCommands;

  public GamesEndpoint(GameCommands gameCommands) {
    this.gameCommands = gameCommands;
  }

  @PostMapping("games/new")
  public void requestGame(@CookieValue(name = "accountId") UUID accountId) {
    gameCommands.requestNewGame(new AccountId(accountId));
  }

  @PostMapping("games/{gameId}/mark")
  public void createGame(
      @CookieValue(name = "accountId") UUID accountId,
      @PathVariable UUID gameId,
      @RequestBody SquareToMark squareToMark) {
    gameCommands.markSquare(new GameId(gameId), new PlayerId(accountId), squareToMark);
  }
}
