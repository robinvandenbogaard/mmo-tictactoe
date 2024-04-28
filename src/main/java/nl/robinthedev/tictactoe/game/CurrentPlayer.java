package nl.robinthedev.tictactoe.game;

import nl.robinthedev.tictactoe.game.model.PlayerId;

record CurrentPlayer(PlayerId ref) {

  static CurrentPlayer create(PlayerId playerId) {
    return new CurrentPlayer(playerId);
  }

  boolean hasId(PlayerId playerId) {
    return ref().equals(playerId);
  }

  PlayerId next(PlayerX playerX, PlayerO playerO) {
    if (hasId(playerX.ref())) {
      return playerO.ref();
    } else {
      return playerX.ref();
    }
  }

  public boolean isNot(PlayerId playerId) {
    return !ref.equals(playerId);
  }
}
