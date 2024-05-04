package nl.robinthedev.tictactoe.account.api.events;

import nl.robinthedev.tictactoe.account.api.AccountId;
import nl.robinthedev.tictactoe.account.api.Username;

public record UsernameUpdated(
    AccountId accountId, Username newUsername, Username previousUsername) {}
