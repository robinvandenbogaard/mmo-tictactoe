package nl.robinthedev.tictactoe.account.api.events;

import nl.robinthedev.tictactoe.account.api.RankeeId;
import nl.robinthedev.tictactoe.account.api.Username;

public record UsernameUpdated(
    RankeeId accountId, Username newUsername, Username previousUsername) {}
