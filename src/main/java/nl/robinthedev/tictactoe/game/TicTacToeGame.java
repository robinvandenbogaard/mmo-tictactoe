package nl.robinthedev.tictactoe.game;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.ArrayList;
import nl.robinthedev.tictactoe.game.commands.MarkSquare;
import nl.robinthedev.tictactoe.game.commands.StartNewGame;
import nl.robinthedev.tictactoe.game.events.GameDraw;
import nl.robinthedev.tictactoe.game.events.GameFinished;
import nl.robinthedev.tictactoe.game.events.MarkSquareRejectedGameIsOver;
import nl.robinthedev.tictactoe.game.events.MarkSquareRejectedNotThePlayersTurn;
import nl.robinthedev.tictactoe.game.events.MarkSquareRejectedSquareAlreadyTaken;
import nl.robinthedev.tictactoe.game.events.NewGameStarted;
import nl.robinthedev.tictactoe.game.events.SquareMarked;
import nl.robinthedev.tictactoe.game.events.TicTacToeEvent;
import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.PlayerId;
import nl.robinthedev.tictactoe.game.model.SquareToMark;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
class TicTacToeGame {

  @AggregateIdentifier GameId gameId;
  Players players;
  Grid grid;
  boolean gameOver = false;

  public TicTacToeGame() {
    // required by Axon Framework
  }

  @CommandHandler
  @CreationPolicy(AggregateCreationPolicy.ALWAYS)
  void on(StartNewGame command) {
    apply(
        NewGameStarted.withPlayerXStarting(command.gameId(), command.playerX(), command.playerO()));
  }

  @EventSourcingHandler
  void handle(NewGameStarted event) {
    gameId = event.gameId();
    players = Players.createPlayers(event.playerX(), event.playerO(), event.startingPlayer());
    grid = Grid.empty();
  }

  @CommandHandler
  void on(MarkSquare command) {
    if (gameOver) {
      apply(
          new MarkSquareRejectedGameIsOver(
              gameId, command.playerRequestingMarking(), grid.toNewGridState()));
      return;
    }

    var player = command.playerRequestingMarking();
    var currentPlayer = players.currentPlayer();
    if (currentPlayer.isNot(player)) {
      apply(
          new MarkSquareRejectedNotThePlayersTurn(
              gameId, currentPlayer.ref(), player, grid.toNewGridState()));
      return;
    }

    var squareToMark = command.squareToMark();
    if (grid.isSquareMarked(squareToMark)) {
      apply(new MarkSquareRejectedSquareAlreadyTaken(gameId, player, grid.toNewGridState()));
      return;
    }

    doMarkSquare(squareToMark, currentPlayer, players.getNextPlayer())
        .forEach(AggregateLifecycle::apply);
  }

  private ArrayList<TicTacToeEvent> doMarkSquare(
      SquareToMark squareToMark, CurrentPlayer currentPlayer, PlayerId nextPlayer) {
    var events = new ArrayList<TicTacToeEvent>();

    var move = new Move(squareToMark, currentPlayer.playerSymbol());
    var updatedGrid = grid.markSquare(move);

    var newGridState = updatedGrid.toNewGridState();
    var markedSquare = move.toMarkedSquare();

    var squareMarked = new SquareMarked(gameId, markedSquare, newGridState, nextPlayer);
    events.add(squareMarked);

    if (updatedGrid.hasWinner()) {
      events.add(
          new GameFinished(gameId, currentPlayer.playerSymbol(), currentPlayer.ref(), nextPlayer));
    } else if (updatedGrid.isFullGrid()) {
      events.add(new GameDraw(gameId, players.playerX().ref(), players.playerO().ref()));
    }
    return events;
  }

  @EventSourcingHandler
  void handle(SquareMarked event) {
    grid = Grid.from(event.gridState());
    players = players.setNextPlayer(event.nextPlayer());
  }

  @EventSourcingHandler
  void handle(GameFinished event) {
    gameOver = true;
  }

  @EventSourcingHandler
  void handle(GameDraw event) {
    gameOver = true;
  }
}
