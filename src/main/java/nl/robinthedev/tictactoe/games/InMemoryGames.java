package nl.robinthedev.tictactoe.games;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.PlayerId;
import nl.robinthedev.tictactoe.games.model.RunningGame;
import nl.robinthedev.tictactoe.games.model.RunningGames;
import org.springframework.stereotype.Service;

@Service
class InMemoryGames implements Games {

  private final Map<GameId, Game> games = new ConcurrentHashMap<>();

  @Override
  public void gameStarted(Game game) {
    games.put(game.id(), game);
  }

  @Override
  public void remove(GameId gameId) {
    games.remove(gameId);
  }

  @Override
  public RunningGames getRunningGames(PlayerId playerId) {
    var runningGames =
        games.values().stream()
            .filter(game -> game.isPlayer(playerId))
            .map(this::toRunningGame)
            .toList();
    return new RunningGames(playerId, runningGames);
  }

  @Override
  public Game get(GameId gameId) {
    return games.get(gameId);
  }

  @Override
  public void update(Game game) {
    games.replace(game.id(), game);
  }

  private RunningGame toRunningGame(Game game) {
    return new RunningGame(game.id(), game.grid(), game.currentPlayer());
  }
}
