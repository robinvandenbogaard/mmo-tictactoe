package nl.robinthedev.tictactoe.lobby;

import nl.robinthedev.tictactoe.lobby.api.RunningGames;
import nl.robinthedev.tictactoe.lobby.api.queries.FetchRunningGames;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Service
class GamesQueryHandler {

  private final Games games;

  GamesQueryHandler(Games games) {
    this.games = games;
  }

  @QueryHandler
  public RunningGames handle(FetchRunningGames query) {
    return games.getRunningGames(query.playerId());
  }
}
