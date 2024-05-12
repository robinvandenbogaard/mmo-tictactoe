package nl.robinthedev.tictactoe.botaccount.events;

import nl.robinthedev.tictactoe.account.api.AccountId;
import nl.robinthedev.tictactoe.account.api.Username;

public record BotAccountCreated(AccountId accountId, Username username) {}
