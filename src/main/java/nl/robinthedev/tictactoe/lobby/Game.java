package nl.robinthedev.tictactoe.lobby;

import java.util.List;
import nl.robinthedev.tictactoe.messaging.game.GameId;
import nl.robinthedev.tictactoe.messaging.game.NewGridState;
import nl.robinthedev.tictactoe.messaging.game.PlayerId;
import nl.robinthedev.tictactoe.messaging.game.StartingPlayer;
import nl.robinthedev.tictactoe.messaging.game.events.NewGameStarted;
import nl.robinthedev.tictactoe.messaging.lobby.Grid;
import nl.robinthedev.tictactoe.messaging.lobby.Mark;

record Game(GameId id, List<PlayerId> players, PlayerId currentPlayer, Grid grid) {
  public static Game of(NewGameStarted event) {
    var currentPlayer =
        event.startingPlayer().equals(StartingPlayer.X) ? event.playerX() : event.playerO();
    var allPlayers = List.of(event.playerX(), event.playerO());
    return new Game(event.gameId(), allPlayers, currentPlayer, Grid.empty());
  }

  public boolean isPlayer(PlayerId playerId) {
    return players.contains(playerId);
  }

  public Game updateGrid(NewGridState newGridState) {
    return new Game(id, players, currentPlayer, toGrid(newGridState));
  }

  private Grid toGrid(NewGridState newGridState) {
    var grid = newGridState.squares().stream().map(Mark::valueOf).toList();
    return new Grid(grid);
  }

  public Game changeCurrentPlayer(PlayerId newCurrentPlayer) {
    return new Game(id, players, newCurrentPlayer, grid);
  }
}
