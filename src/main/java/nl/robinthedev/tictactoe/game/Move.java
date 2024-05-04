package nl.robinthedev.tictactoe.game;

import nl.robinthedev.tictactoe.game.api.MarkedSquare;
import nl.robinthedev.tictactoe.game.api.PlayerSymbol;
import nl.robinthedev.tictactoe.game.api.SquareToMark;

record Move(SquareToMark squareToMark, PlayerSymbol playerSymbol) {
  public MarkedSquare toMarkedSquare() {
    return new MarkedSquare(squareToMark.column(), squareToMark.row(), playerSymbol);
  }
}
