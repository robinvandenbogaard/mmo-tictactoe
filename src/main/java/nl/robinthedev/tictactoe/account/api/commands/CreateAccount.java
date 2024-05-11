package nl.robinthedev.tictactoe.account.api.commands;

import nl.robinthedev.tictactoe.account.api.RankeeId;
import nl.robinthedev.tictactoe.account.api.Username;

public record CreateAccount(RankeeId accountId, Username username) {}
