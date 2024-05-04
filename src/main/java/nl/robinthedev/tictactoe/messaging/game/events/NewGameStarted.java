package nl.robinthedev.tictactoe.messaging.game.events;

import nl.robinthedev.tictactoe.messaging.game.GameId;
import nl.robinthedev.tictactoe.messaging.game.PlayerId;
import nl.robinthedev.tictactoe.messaging.game.StartingPlayer;

public record NewGameStarted(
        GameId gameId, PlayerId playerX, PlayerId playerO, StartingPlayer startingPlayer)
    implements TicTacToeEvent {

  public static NewGameStarted withPlayerXStarting(
      GameId gameId, PlayerId playerX, PlayerId playerO) {
    return new NewGameStarted(gameId, playerX, playerO, StartingPlayer.X);
  }
}
