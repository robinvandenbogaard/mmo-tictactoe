package nl.robinthedev.tictactoe.messaging.lobby.queries;

import nl.robinthedev.tictactoe.messaging.game.PlayerId;

public record FetchRunningGames(PlayerId playerId) {}
