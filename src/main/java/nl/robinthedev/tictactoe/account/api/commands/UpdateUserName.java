package nl.robinthedev.tictactoe.account.api.commands;

import nl.robinthedev.tictactoe.account.api.AccountId;
import nl.robinthedev.tictactoe.account.api.Username;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record UpdateUserName(
    @TargetAggregateIdentifier AccountId accountId, Username desiredUsername) {}
