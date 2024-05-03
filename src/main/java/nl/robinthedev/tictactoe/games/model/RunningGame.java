package nl.robinthedev.tictactoe.games.model;

import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.PlayerId;

public record RunningGame(GameId gameId, Grid grid, PlayerId currentPlayer) {}
