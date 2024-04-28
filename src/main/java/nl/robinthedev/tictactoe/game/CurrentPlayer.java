package nl.robinthedev.tictactoe.game;

import java.util.UUID;
import nl.robinthedev.tictactoe.game.model.Player;

record CurrentPlayer(UUID playerId) {

  static CurrentPlayer create(Player player) {
    return new CurrentPlayer(player.ref());
  }

  boolean hasId(UUID playerId) {
    return playerId().equals(playerId);
  }

  Player next(PlayerX playerX, PlayerO playerO) {
    if (hasId(playerX.uuid())) {
      return new Player(playerO.uuid());
    } else {
      return new Player(playerX.uuid());
    }
  }
}
