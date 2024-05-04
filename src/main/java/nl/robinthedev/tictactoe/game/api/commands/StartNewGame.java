package nl.robinthedev.tictactoe.game.api.commands;

import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.PlayerId;

public record StartNewGame(GameId gameId, PlayerId playerX, PlayerId playerO) {}
