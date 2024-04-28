package nl.robinthedev.tictactoe.game;

import nl.robinthedev.tictactoe.game.model.MarkedSquare;
import nl.robinthedev.tictactoe.game.model.NewGridState;

sealed interface MarkResult permits MarkResult.SquareAlreadyMarked, MarkResult.ValidMarking {
  record ValidMarking(MarkedSquare markedSquare, NewGridState newGridState) implements MarkResult {}

  record SquareAlreadyMarked() implements MarkResult {}
}
