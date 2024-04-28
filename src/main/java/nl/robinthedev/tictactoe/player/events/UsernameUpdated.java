package nl.robinthedev.tictactoe.player.events;

import nl.robinthedev.tictactoe.player.model.PlayerId;
import nl.robinthedev.tictactoe.player.model.Username;

public record UsernameUpdated(PlayerId playerId, Username newUsername, Username previousUsername) {}
