package nl.robinthedev.tictactoe.lobby.api;

import java.time.LocalDateTime;
import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.game.api.PlayerSymbol;

public record RunningGame(
    GameId gameId,
    Grid grid,
    PlayerId currentPlayer,
    PlayerSymbol currentSymbol,
    LocalDateTime lastActivity) {}
