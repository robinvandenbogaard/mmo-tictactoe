package nl.robinthedev.tictactoe.game;

import nl.robinthedev.tictactoe.game.model.MarkedSquare;
import nl.robinthedev.tictactoe.game.model.NewGridState;
import nl.robinthedev.tictactoe.game.model.PlayerSymbol;

sealed interface MarkResult
    permits MarkResult.GameFinishedInDraw,
        MarkResult.GameFinishedWithWinner,
        MarkResult.SquareAlreadyMarked,
        MarkResult.ValidMarking {

  record ValidMarking(MarkedSquare markedSquare, NewGridState newGridState) implements MarkResult {}

  record SquareAlreadyMarked() implements MarkResult {}

  record GameFinishedWithWinner(
      MarkedSquare markedSquare, NewGridState newGridState, PlayerSymbol winningSymbol)
      implements MarkResult {}

  record GameFinishedInDraw(MarkedSquare markedSquare, NewGridState newGridState)
      implements MarkResult {}
}
