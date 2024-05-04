package nl.robinthedev.tictactoe.messaging.game.events;

import nl.robinthedev.tictactoe.messaging.game.GameId;
import nl.robinthedev.tictactoe.messaging.game.NewGridState;
import nl.robinthedev.tictactoe.messaging.game.PlayerId;

public record MarkSquareRejectedNotThePlayersTurn(
        GameId gameId, PlayerId currentPlayer, PlayerId violatingPlayer, NewGridState newGridState)
    implements TicTacToeEvent {}
