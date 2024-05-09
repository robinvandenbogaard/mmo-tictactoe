package nl.robinthedev.tictactoe.game.api.events;

import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.MarkedSquare;
import nl.robinthedev.tictactoe.game.api.NewGridState;
import nl.robinthedev.tictactoe.game.api.PlayerId;

public record SquareMarked(
    GameId gameId, MarkedSquare markedSquare, NewGridState gridState, PlayerId nextPlayer)
    implements TicTacToeEvent {}
