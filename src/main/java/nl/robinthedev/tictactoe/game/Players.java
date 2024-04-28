package nl.robinthedev.tictactoe.game;

import nl.robinthedev.tictactoe.game.model.PlayerId;
import nl.robinthedev.tictactoe.game.model.PlayerSymbol;
import nl.robinthedev.tictactoe.game.model.StartingPlayer;

record Players(PlayerX playerX, PlayerO playerO, CurrentPlayer currentPlayer) {

  public static Players createPlayers(
      PlayerId playerX, PlayerId playerO, StartingPlayer startingPlayer) {
    var currentPlayer =
        switch (startingPlayer) {
          case X -> CurrentPlayer.create(playerX, PlayerSymbol.X);
          case O -> CurrentPlayer.create(playerO, PlayerSymbol.O);
        };
    return new Players(PlayerX.create(playerX), PlayerO.create(playerO), currentPlayer);
  }

  public boolean isPlayerTurn(PlayerId playerId) {
    return currentPlayer.hasId(playerId);
  }

  public PlayerId getNextPlayer() {
    return currentPlayer.next(playerX, playerO);
  }

  public Players setNextPlayer(PlayerId playerId) {
    if (currentPlayer.hasId(playerId))
      throw new IllegalArgumentException(
          "Next player can't be changed to the current player. Expected another playerId.");

    var symbol = playerId.equals(playerX.ref()) ? PlayerSymbol.X : PlayerSymbol.O;
    return new Players(playerX, playerO, CurrentPlayer.create(playerId, symbol));
  }
}
