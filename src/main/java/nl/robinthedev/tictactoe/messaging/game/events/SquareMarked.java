package nl.robinthedev.tictactoe.messaging.game.events;

import nl.robinthedev.tictactoe.messaging.game.GameId;
import nl.robinthedev.tictactoe.messaging.game.MarkedSquare;
import nl.robinthedev.tictactoe.messaging.game.NewGridState;
import nl.robinthedev.tictactoe.messaging.game.PlayerId;

public record SquareMarked(
        GameId gameId, MarkedSquare markedSquare, NewGridState gridState, PlayerId nextPlayer)
    implements TicTacToeEvent {}
