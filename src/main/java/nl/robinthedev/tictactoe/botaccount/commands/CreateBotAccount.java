package nl.robinthedev.tictactoe.botaccount.commands;

import nl.robinthedev.tictactoe.account.api.AccountId;
import nl.robinthedev.tictactoe.account.api.Username;

public record CreateBotAccount(AccountId accountId, Username username) {}
