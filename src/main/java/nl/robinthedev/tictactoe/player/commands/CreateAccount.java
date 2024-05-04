package nl.robinthedev.tictactoe.player.commands;

import nl.robinthedev.tictactoe.player.model.AccountId;
import nl.robinthedev.tictactoe.player.model.Username;

public record CreateAccount(AccountId accountId, Username username) {}
