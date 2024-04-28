package nl.robinthedev.tictactoe.game;

import java.util.UUID;
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

  public boolean isPlayerTurn(UUID uuid) {
    return currentPlayer.hasId(uuid);
  }

  public PlayerSymbol getSymbolForCurrentPlayer() {
    return PlayerSymbol.X;
  }

  public Player getNextPlayer() {
    return currentPlayer.next(playerX, playerO);
  }

  public Players setNextPlayer(Player player) {
    return new Players(playerX, playerO, CurrentPlayer.create(player));
  }
}
