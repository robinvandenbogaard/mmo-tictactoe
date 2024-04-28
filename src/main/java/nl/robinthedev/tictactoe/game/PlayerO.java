package nl.robinthedev.tictactoe.game;

import nl.robinthedev.tictactoe.game.model.PlayerId;

record PlayerO(PlayerId ref) {

  static PlayerO create(PlayerId playerId) {
    return new PlayerO(playerId);
  }
}
