package nl.robinthedev.tictactoe.game.events;

import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.MarkedSquare;
import nl.robinthedev.tictactoe.game.model.NewGridState;
import nl.robinthedev.tictactoe.game.model.PlayerId;

public record SquareMarked(
    GameId id, MarkedSquare markedSquare, NewGridState gridState, PlayerId nextPlayer)
    implements TicTacToeEvent {}
