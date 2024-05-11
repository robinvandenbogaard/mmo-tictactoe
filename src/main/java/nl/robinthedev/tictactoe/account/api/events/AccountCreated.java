package nl.robinthedev.tictactoe.account.api.events;

import nl.robinthedev.tictactoe.account.api.RankeeId;
import nl.robinthedev.tictactoe.account.api.Username;

public record AccountCreated(RankeeId accountId, Username username) {}
