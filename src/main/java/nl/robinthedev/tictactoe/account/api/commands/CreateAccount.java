package nl.robinthedev.tictactoe.account.api.commands;

import nl.robinthedev.tictactoe.account.api.AccountId;
import nl.robinthedev.tictactoe.account.api.Username;

public record CreateAccount(AccountId accountId, Username username) {}
