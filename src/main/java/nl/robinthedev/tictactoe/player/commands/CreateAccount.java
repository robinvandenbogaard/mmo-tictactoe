package nl.robinthedev.tictactoe.player.commands;

import nl.robinthedev.tictactoe.player.model.PlayerId;
import nl.robinthedev.tictactoe.player.model.Username;

public record CreateAccount(PlayerId playerId, Username username) {}
