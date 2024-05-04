package nl.robinthedev.tictactoe.game.api.events;

import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.game.api.StartingPlayer;

public record NewGameStarted(
        GameId gameId, PlayerId playerX, PlayerId playerO, StartingPlayer startingPlayer)
    implements TicTacToeEvent {

  public static NewGameStarted withPlayerXStarting(
      GameId gameId, PlayerId playerX, PlayerId playerO) {
    return new NewGameStarted(gameId, playerX, playerO, StartingPlayer.X);
  }
}
