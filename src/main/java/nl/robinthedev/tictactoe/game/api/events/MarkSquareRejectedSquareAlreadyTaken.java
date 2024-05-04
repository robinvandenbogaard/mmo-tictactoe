package nl.robinthedev.tictactoe.game.api.events;

import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.NewGridState;
import nl.robinthedev.tictactoe.game.api.PlayerId;

public record MarkSquareRejectedSquareAlreadyTaken(
        GameId gameId, PlayerId violatingPlayer, NewGridState newGridState) implements TicTacToeEvent {}
