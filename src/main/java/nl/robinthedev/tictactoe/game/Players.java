package nl.robinthedev.tictactoe.game;

import nl.robinthedev.tictactoe.game.model.Player;
import nl.robinthedev.tictactoe.game.model.PlayerSymbol;
import nl.robinthedev.tictactoe.game.model.StartingPlayer;

record Players(PlayerX playerX, PlayerO playerO, CurrentPlayer currentPlayer) {

  public static Players createPlayers(
      Player playerX, Player playerO, StartingPlayer startingPlayer) {
    var currentPlayer =
        switch (startingPlayer) {
          case X -> CurrentPlayer.create(playerX);
          case O -> CurrentPlayer.create(playerO);
        };
    return new Players(PlayerX.create(playerX), PlayerO.create(playerO), currentPlayer);
  }

  public boolean isPlayerTurn(Player player) {
    return currentPlayer.hasId(player.ref());
  }

  public PlayerSymbol getSymbolForCurrentPlayer() {
    return PlayerSymbol.X;
  }

  public Player getCurrentPlayer() {
    return new Player(currentPlayer.playerId());
  }

  public Player getNextPlayer() {
    return currentPlayer.next(playerX, playerO);
  }

  public Players setNextPlayer(Player player) {
    return new Players(playerX, playerO, CurrentPlayer.create(player));
  }

  public boolean isNotPlayerTurn(Player player) {
    return !isPlayerTurn(player);
  }
}
