package nl.robinthedev.tictactoe.lobby;

import java.util.List;
import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.NewGridState;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.game.api.StartingPlayer;
import nl.robinthedev.tictactoe.game.api.events.NewGameStarted;
import nl.robinthedev.tictactoe.lobby.api.Grid;
import nl.robinthedev.tictactoe.lobby.api.Mark;

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
