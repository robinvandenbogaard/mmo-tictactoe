package nl.robinthedev.tictactoe.player.events;

import nl.robinthedev.tictactoe.player.model.PlayerId;
import nl.robinthedev.tictactoe.player.model.Username;

public record AccountCreated(PlayerId playerId, Username username) {}
