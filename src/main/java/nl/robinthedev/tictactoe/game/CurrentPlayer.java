package nl.robinthedev.tictactoe.game;

import java.util.UUID;

record CurrentPlayer(UUID playerId) {

  static CurrentPlayer create(PlayerX playerX) {
    return new CurrentPlayer(playerX.uuid());
  }

  static CurrentPlayer create(PlayerO playerO) {
    return new CurrentPlayer(playerO.uuid());
  }

  boolean hasId(UUID playerId) {
    return playerId().equals(playerId);
  }
}
