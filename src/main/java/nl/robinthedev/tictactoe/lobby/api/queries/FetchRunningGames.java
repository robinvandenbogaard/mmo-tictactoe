package nl.robinthedev.tictactoe.lobby.api.queries;

import nl.robinthedev.tictactoe.game.api.PlayerId;

public record FetchRunningGames(PlayerId playerId) {}
