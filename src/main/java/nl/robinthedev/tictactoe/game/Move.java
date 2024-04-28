package nl.robinthedev.tictactoe.game;

import nl.robinthedev.tictactoe.game.model.MarkedSquare;
import nl.robinthedev.tictactoe.game.model.PlayerSymbol;
import nl.robinthedev.tictactoe.game.model.SquareToMark;

record Move(SquareToMark squareToMark, PlayerSymbol playerSymbol) {
  public MarkedSquare toMarkedSquare() {
    return new MarkedSquare(squareToMark.column(), squareToMark.row(), playerSymbol);
  }
}
