package nl.robinthedev.tictactoe.lobby;

import java.util.UUID;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.lobby.api.RunningGames;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class LobbyEndpoint {

  private static final PlayerId onlyHuman =
      new PlayerId(UUID.fromString("1738158c-9d8f-4343-9203-9a081cee4a0f"));

  private final Games games;

  public LobbyEndpoint(Games games) {
    this.games = games;
  }

  @GetMapping("lobby/games")
  public RunningGames listActiveGames() {
    return games.getRunningGames(onlyHuman);
  }
}
