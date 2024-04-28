package nl.robinthedev.tictactoe.game.commands;

import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.Player;

public record StartNewGame(GameId gameId, Player playerX, Player playerO) {}
