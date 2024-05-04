package nl.robinthedev.tictactoe.game;

import nl.robinthedev.tictactoe.game.api.PlayerId;

record PlayerO(PlayerId ref) {

  static PlayerO create(PlayerId playerId) {
    return new PlayerO(playerId);
  }
}
