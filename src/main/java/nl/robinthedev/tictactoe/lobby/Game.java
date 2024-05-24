package nl.robinthedev.tictactoe.lobby;

import java.time.LocalDateTime;
import java.util.List;
import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.NewGridState;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.game.api.PlayerSymbol;
import nl.robinthedev.tictactoe.game.api.StartingPlayer;
import nl.robinthedev.tictactoe.game.api.events.NewGameStarted;
import nl.robinthedev.tictactoe.lobby.api.Grid;
import nl.robinthedev.tictactoe.lobby.api.Mark;

record Game(
    GameId id,
    List<PlayerId> players,
    PlayerId currentPlayer,
    PlayerSymbol currentSymbol,
    Grid grid,
    LocalDateTime lastActivity) {
  public static Game of(NewGameStarted event) {
    var currentPlayer =
        event.startingPlayer().equals(StartingPlayer.X) ? event.playerX() : event.playerO();
    var currentSymbol =
        event.startingPlayer().equals(StartingPlayer.X) ? PlayerSymbol.X : PlayerSymbol.O;
    var allPlayers = List.of(event.playerX(), event.playerO());
    return new Game(
        event.gameId(),
        allPlayers,
        currentPlayer,
        currentSymbol,
        Grid.empty(),
        LocalDateTime.now());
  }

  public boolean isPlayer(PlayerId playerId) {
    return players.contains(playerId);
  }

  public Game updateGrid(NewGridState newGridState) {
    return new Game(
        id, players, currentPlayer, currentSymbol, toGrid(newGridState), LocalDateTime.now());
  }

  private Grid toGrid(NewGridState newGridState) {
    var grid = newGridState.squares().stream().map(Mark::valueOf).toList();
    return new Grid(grid);
  }

  public Game changeCurrentPlayer(PlayerId newCurrentPlayer) {
    return new Game(id, players, newCurrentPlayer, currentSymbol, grid, LocalDateTime.now());
  }

  public Game changeToOtherSymbol(PlayerSymbol symbol) {
    var newSymbol =
        switch (symbol) {
          case X -> PlayerSymbol.O;
          case O -> PlayerSymbol.X;
        };
    return new Game(id, players, currentPlayer, newSymbol, grid, LocalDateTime.now());
  }
}
