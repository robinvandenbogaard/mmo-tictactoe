package nl.robinthedev.tictactoe.game;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import nl.robinthedev.tictactoe.game.commands.MarkSquare;
import nl.robinthedev.tictactoe.game.commands.StartNewGame;
import nl.robinthedev.tictactoe.game.events.NewGameStarted;
import nl.robinthedev.tictactoe.game.events.SquareMarked;
import nl.robinthedev.tictactoe.game.model.GameId;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
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
    var markResult = grid.markSquare(command.squareToMark(), players.getSymbolForCurrentPlayer());
    apply(
        new SquareMarked(
            gameId, markResult.markedSquare(), markResult.newGridState(), players.getNextPlayer()));
  }

  @EventSourcingHandler
  void handle(SquareMarked event) {
    grid = Grid.from(event.gridState());
    players = players.setNextPlayer(event.nextPlayer());
  }
}
