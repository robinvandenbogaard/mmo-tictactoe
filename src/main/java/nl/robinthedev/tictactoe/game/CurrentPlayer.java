package nl.robinthedev.tictactoe.game;

import nl.robinthedev.tictactoe.messaging.game.PlayerId;
import nl.robinthedev.tictactoe.messaging.game.PlayerSymbol;

record CurrentPlayer(PlayerId ref, PlayerSymbol playerSymbol) {

  static CurrentPlayer create(PlayerId playerId, PlayerSymbol symbol) {
    return new CurrentPlayer(playerId, symbol);
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
