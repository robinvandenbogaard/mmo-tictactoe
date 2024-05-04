package nl.robinthedev.tictactoe.game;

import nl.robinthedev.tictactoe.messaging.game.MarkedSquare;
import nl.robinthedev.tictactoe.messaging.game.PlayerSymbol;
import nl.robinthedev.tictactoe.messaging.game.SquareToMark;

record Move(SquareToMark squareToMark, PlayerSymbol playerSymbol) {
  public MarkedSquare toMarkedSquare() {
    return new MarkedSquare(squareToMark.column(), squareToMark.row(), playerSymbol);
  }
}
