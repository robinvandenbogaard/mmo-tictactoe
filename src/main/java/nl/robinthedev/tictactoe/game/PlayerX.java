package nl.robinthedev.tictactoe.game;

import java.util.UUID;
import nl.robinthedev.tictactoe.game.model.Player;

record PlayerX(UUID uuid) {
  static PlayerX create(Player player) {
    return new PlayerX(player.ref());
  }
}
