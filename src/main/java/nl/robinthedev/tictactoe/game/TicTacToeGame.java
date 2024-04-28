package nl.robinthedev.tictactoe.game;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.List;
import nl.robinthedev.tictactoe.game.MarkResult.GameFinished;
import nl.robinthedev.tictactoe.game.MarkResult.SquareAlreadyMarked;
import nl.robinthedev.tictactoe.game.MarkResult.ValidMarking;
import nl.robinthedev.tictactoe.game.commands.MarkSquare;
import nl.robinthedev.tictactoe.game.commands.StartNewGame;
import nl.robinthedev.tictactoe.game.events.GameLost;
import nl.robinthedev.tictactoe.game.events.GameWon;
import nl.robinthedev.tictactoe.game.events.MarkSquareRejectedNotThePlayersTurn;
import nl.robinthedev.tictactoe.game.events.MarkSquareRejectedSquareAlreadyTaken;
import nl.robinthedev.tictactoe.game.events.NewGameStarted;
import nl.robinthedev.tictactoe.game.events.SquareMarked;
import nl.robinthedev.tictactoe.game.model.GameId;
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
    var player = command.playerRequestingMarking();
    var currentPlayer = players.currentPlayer();

    if (currentPlayer.isNot(player)) {
      apply(new MarkSquareRejectedNotThePlayersTurn(gameId, currentPlayer.ref(), player));
      return;
    }

    var markResult = grid.markSquare(command.squareToMark(), currentPlayer.playerSymbol());
    var events =
        switch (markResult) {
          case ValidMarking(var square, var gridState) ->
              List.of(new SquareMarked(gameId, square, gridState, players.getNextPlayer()));
          case SquareAlreadyMarked() ->
              List.of(new MarkSquareRejectedSquareAlreadyTaken(gameId, player));
          case GameFinished(var square, var gridState, var winningSymbol) ->
              List.of(
                  new SquareMarked(gameId, square, gridState, players.getNextPlayer()),
                  new GameWon(gameId, players.getPlayerWithSymbol(winningSymbol)),
                  new GameLost(gameId, players.getPlayerWithSymbol(winningSymbol.other())));
        };
    events.forEach(AggregateLifecycle::apply);
  }

  @EventSourcingHandler
  void handle(SquareMarked event) {
    grid = Grid.from(event.gridState());
    players = players.setNextPlayer(event.nextPlayer());
  }
}
