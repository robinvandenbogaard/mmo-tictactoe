package nl.robinthedev.tictactoe.lobby;

import nl.robinthedev.tictactoe.messaging.game.GameId;
import nl.robinthedev.tictactoe.messaging.game.PlayerId;
import nl.robinthedev.tictactoe.messaging.lobby.RunningGames;

interface Games {
  void gameStarted(Game game);

  void remove(GameId gameId);

  RunningGames getRunningGames(PlayerId playerId);

  Game get(GameId gameId);

  void update(Game game);
}
