package nl.robinthedev.tictactoe.game.commands;

import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.PlayerId;

public record StartNewGame(GameId gameId, PlayerId playerX, PlayerId playerO) {}
