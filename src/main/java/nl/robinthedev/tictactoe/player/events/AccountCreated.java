package nl.robinthedev.tictactoe.player.events;

import nl.robinthedev.tictactoe.player.model.AccountId;
import nl.robinthedev.tictactoe.player.model.Username;

public record AccountCreated(AccountId accountId, Username username) {}
