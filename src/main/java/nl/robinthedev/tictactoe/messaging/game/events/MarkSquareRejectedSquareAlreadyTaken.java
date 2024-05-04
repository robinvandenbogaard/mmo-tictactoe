package nl.robinthedev.tictactoe.messaging.game.events;

import nl.robinthedev.tictactoe.messaging.game.GameId;
import nl.robinthedev.tictactoe.messaging.game.NewGridState;
import nl.robinthedev.tictactoe.messaging.game.PlayerId;

public record MarkSquareRejectedSquareAlreadyTaken(
        GameId gameId, PlayerId violatingPlayer, NewGridState newGridState) implements TicTacToeEvent {}
