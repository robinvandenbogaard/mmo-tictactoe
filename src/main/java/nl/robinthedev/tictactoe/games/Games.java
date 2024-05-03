package nl.robinthedev.tictactoe.games;

import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.PlayerId;
import nl.robinthedev.tictactoe.games.model.RunningGames;

interface Games {
  void gameStarted(Game game);

  void remove(GameId gameId);

  RunningGames getRunningGames(PlayerId playerId);

  Game get(GameId gameId);

  void update(Game game);
}
