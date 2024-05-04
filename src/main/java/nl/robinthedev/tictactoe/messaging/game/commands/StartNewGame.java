package nl.robinthedev.tictactoe.messaging.game.commands;

import nl.robinthedev.tictactoe.messaging.game.GameId;
import nl.robinthedev.tictactoe.messaging.game.PlayerId;

public record StartNewGame(GameId gameId, PlayerId playerX, PlayerId playerO) {}
