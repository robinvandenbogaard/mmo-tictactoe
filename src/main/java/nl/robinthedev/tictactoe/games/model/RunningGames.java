package nl.robinthedev.tictactoe.games.model;

import java.util.List;
import nl.robinthedev.tictactoe.game.model.PlayerId;

public record RunningGames(PlayerId playerId, List<RunningGame> games) {}
