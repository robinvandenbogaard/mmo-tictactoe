package nl.robinthedev.tictactoe.game;

import java.util.UUID;
import nl.robinthedev.tictactoe.game.model.Player;

class Players {
  private final PlayerX playerX;
  private final PlayerO playerO;
  private CurrentPlayer currentPlayer;

  public Players(Player playerX, Player playerO) {
    this.playerX = PlayerX.create(playerX);
    this.playerO = PlayerO.create(playerO);
  }

  public void nextMoveisForPlayerX() {
    currentPlayer = CurrentPlayer.create(playerX);
  }

  public void nextMoveisForPlayerO() {
    currentPlayer = CurrentPlayer.create(playerO);
  }

  public boolean isPlayerTurn(UUID uuid) {
    return currentPlayer.hasId(uuid);
  }
}
