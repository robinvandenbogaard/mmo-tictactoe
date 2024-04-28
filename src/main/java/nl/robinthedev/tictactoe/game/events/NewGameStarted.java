package nl.robinthedev.tictactoe.game.events;

import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.PlayerId;
import nl.robinthedev.tictactoe.game.model.StartingPlayer;

public record NewGameStarted(
    GameId gameId, PlayerId playerX, PlayerId playerO, StartingPlayer startingPlayer) {

  public static NewGameStarted withPlayerXStarting(
      GameId gameId, PlayerId playerX, PlayerId playerO) {
    return new NewGameStarted(gameId, playerX, playerO, StartingPlayer.X);
  }
}
