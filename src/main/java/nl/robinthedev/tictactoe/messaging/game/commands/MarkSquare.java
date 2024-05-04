package nl.robinthedev.tictactoe.messaging.game.commands;

import nl.robinthedev.tictactoe.messaging.game.GameId;
import nl.robinthedev.tictactoe.messaging.game.PlayerId;
import nl.robinthedev.tictactoe.messaging.game.SquareToMark;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record MarkSquare(
    @TargetAggregateIdentifier GameId gameId,
    PlayerId playerRequestingMarking,
    SquareToMark squareToMark) {}
