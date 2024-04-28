package nl.robinthedev.tictactoe.game.events;

import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.Player;
import nl.robinthedev.tictactoe.game.model.StartingPlayer;

public record NewGameStarted(
    GameId gameId, Player playerX, Player playerO, StartingPlayer startingPlayer) {

  public static NewGameStarted withPlayerXStarting(GameId gameId, Player playerX, Player playerO) {
    return new NewGameStarted(gameId, playerX, playerO, StartingPlayer.X);
  }
}
