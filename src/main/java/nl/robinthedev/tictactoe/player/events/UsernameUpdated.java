package nl.robinthedev.tictactoe.player.events;

import nl.robinthedev.tictactoe.player.model.AccountId;
import nl.robinthedev.tictactoe.player.model.Username;

public record UsernameUpdated(
    AccountId accountId, Username newUsername, Username previousUsername) {}
