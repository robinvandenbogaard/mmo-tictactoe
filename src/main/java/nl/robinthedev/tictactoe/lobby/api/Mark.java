package nl.robinthedev.tictactoe.lobby.api;

import nl.robinthedev.tictactoe.game.api.SquareSymbol;

public enum Mark {
  X,
  O,
  EMPTY;

  public static Mark valueOf(SquareSymbol squareSymbol) {
    return switch (squareSymbol) {
      case X -> X;
      case O -> O;
      case EMPTY -> EMPTY;
    };
  }
}
