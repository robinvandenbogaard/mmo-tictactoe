package nl.robinthedev.tictactoe.games.model;

import nl.robinthedev.tictactoe.game.model.SquareSymbol;

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
