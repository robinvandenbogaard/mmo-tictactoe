package nl.robinthedev.tictactoe.lobby.api;

import java.util.List;
import nl.robinthedev.tictactoe.game.api.PlayerId;

public record RunningGames(PlayerId playerId, List<RunningGame> games) {}
