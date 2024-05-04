package nl.robinthedev.tictactoe.games.model;

import nl.robinthedev.tictactoe.messaging.game.GameId;
import nl.robinthedev.tictactoe.messaging.game.PlayerId;

public record RunningGame(GameId gameId, Grid grid, PlayerId currentPlayer) {}
