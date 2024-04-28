package nl.robinthedev.tictactoe.game;

import java.util.UUID;
import nl.robinthedev.tictactoe.game.model.Player;

record PlayerO(UUID uuid) {

  static PlayerO create(Player player) {
    return new PlayerO(player.ref());
  }
}
