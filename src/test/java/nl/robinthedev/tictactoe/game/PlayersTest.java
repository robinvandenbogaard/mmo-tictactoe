package nl.robinthedev.tictactoe.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.game.api.StartingPlayer;
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
  void setNextPlayerToSamePlayerIsNotAllowed() {
    var players = Players.createPlayers(playerX, playerO, StartingPlayer.X);

    assertThatThrownBy(() -> players.setNextPlayer(playerX))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
