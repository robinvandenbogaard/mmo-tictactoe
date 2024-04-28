package nl.robinthedev.tictactoe.game.commands;

import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.PlayerId;
import nl.robinthedev.tictactoe.game.model.SquareToMark;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record MarkSquare(
    @TargetAggregateIdentifier GameId gameId,
    PlayerId playerRequestingMarking,
    SquareToMark squareToMark) {}
