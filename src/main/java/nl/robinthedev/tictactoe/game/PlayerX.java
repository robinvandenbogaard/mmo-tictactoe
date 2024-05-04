package nl.robinthedev.tictactoe.game;

import nl.robinthedev.tictactoe.messaging.game.PlayerId;

record PlayerX(PlayerId ref) {
  static PlayerX create(PlayerId playerId) {
    return new PlayerX(playerId);
  }
}
