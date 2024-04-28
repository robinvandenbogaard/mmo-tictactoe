package nl.robinthedev.tictactoe.game;

import nl.robinthedev.tictactoe.game.model.PlayerId;
import nl.robinthedev.tictactoe.game.model.PlayerSymbol;
import nl.robinthedev.tictactoe.game.model.StartingPlayer;

record Players(PlayerX playerX, PlayerO playerO, CurrentPlayer currentPlayer) {

  public static Players createPlayers(
      PlayerId playerX, PlayerId playerO, StartingPlayer startingPlayer) {
    var currentPlayer =
        switch (startingPlayer) {
          case X -> CurrentPlayer.create(playerX);
          case O -> CurrentPlayer.create(playerO);
        };
    return new Players(PlayerX.create(playerX), PlayerO.create(playerO), currentPlayer);
  }

  public boolean isPlayerTurn(PlayerId playerId) {
    return currentPlayer.hasId(playerId);
  }

  public PlayerSymbol getSymbolForCurrentPlayer() {
    return PlayerSymbol.X;
  }

  public PlayerId getNextPlayer() {
    return currentPlayer.next(playerX, playerO);
  }

  public Players setNextPlayer(PlayerId playerId) {
    return new Players(playerX, playerO, CurrentPlayer.create(playerId));
  }
}
