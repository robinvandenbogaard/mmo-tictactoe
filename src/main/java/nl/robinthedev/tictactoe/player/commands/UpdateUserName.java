package nl.robinthedev.tictactoe.player.commands;

import nl.robinthedev.tictactoe.player.model.AccountId;
import nl.robinthedev.tictactoe.player.model.Username;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record UpdateUserName(
    @TargetAggregateIdentifier AccountId accountId, Username desiredUsername) {}
