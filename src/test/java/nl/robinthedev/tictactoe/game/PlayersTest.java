package nl.robinthedev.tictactoe.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import nl.robinthedev.tictactoe.game.model.PlayerId;
import nl.robinthedev.tictactoe.game.model.StartingPlayer;
import org.junit.jupiter.api.Test;

class PlayersTest {

  private final PlayerId playerX = new PlayerId(UUID.randomUUID());
  private final PlayerId playerO = new PlayerId(UUID.randomUUID());

  @Test
  void setNextPlayerToOtherPlayer() {
    var players = Players.createPlayers(playerX, playerO, StartingPlayer.X).setNextPlayer(playerO);
    assertThat(players).isEqualTo(Players.createPlayers(playerX, playerO, StartingPlayer.O));
  }

  @Test
  void setNextPlayerToSamePlayer() {
    var players = Players.createPlayers(playerX, playerO, StartingPlayer.X).setNextPlayer(playerX);
    assertThat(players).isEqualTo(Players.createPlayers(playerX, playerO, StartingPlayer.X));
  }
}
