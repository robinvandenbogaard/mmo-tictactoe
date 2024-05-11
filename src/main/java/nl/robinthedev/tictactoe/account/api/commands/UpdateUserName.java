package nl.robinthedev.tictactoe.account.api.commands;

import nl.robinthedev.tictactoe.account.api.RankeeId;
import nl.robinthedev.tictactoe.account.api.Username;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record UpdateUserName(
    @TargetAggregateIdentifier RankeeId accountId, Username desiredUsername) {}
