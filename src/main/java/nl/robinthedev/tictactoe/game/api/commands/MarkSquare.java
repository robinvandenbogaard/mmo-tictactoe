package nl.robinthedev.tictactoe.game.api.commands;

import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.game.api.SquareToMark;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record MarkSquare(
    @TargetAggregateIdentifier GameId gameId,
    PlayerId playerRequestingMarking,
    SquareToMark squareToMark) {}
