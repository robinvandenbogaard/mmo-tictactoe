package nl.robinthedev.tictactoe.lobby;

import java.util.UUID;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.lobby.api.RunningGames;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class LobbyEndpoint {

  private final Games games;

  public LobbyEndpoint(Games games) {
    this.games = games;
  }

  @GetMapping("lobby/games")
  public RunningGames listActiveGames(@CookieValue(name = "accountId") UUID accountId) {
    return games.getRunningGames(new PlayerId(accountId));
  }
}
