package nl.robinthedev.tictactoe.lobby;

import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.lobby.api.RunningGames;

interface Games {
  void gameStarted(Game game);

  void remove(GameId gameId);

  RunningGames getRunningGames(PlayerId playerId);

  Game get(GameId gameId);

  void update(Game game);
}
