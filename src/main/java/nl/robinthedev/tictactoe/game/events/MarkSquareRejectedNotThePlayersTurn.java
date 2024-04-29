package nl.robinthedev.tictactoe.game.events;

import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.NewGridState;
import nl.robinthedev.tictactoe.game.model.PlayerId;

public record MarkSquareRejectedNotThePlayersTurn(
    GameId gameId, PlayerId currentPlayer, PlayerId violatingPlayer, NewGridState newGridState)
    implements TicTacToeEvent {}
