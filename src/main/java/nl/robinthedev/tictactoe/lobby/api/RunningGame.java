package nl.robinthedev.tictactoe.lobby.api;

import java.time.LocalDateTime;
import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.PlayerId;

public record RunningGame(
    GameId gameId, Grid grid, PlayerId currentPlayer, LocalDateTime lastActivity) {}
