package nl.robinthedev.tictactoe.game;

import java.util.UUID;
import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.MarkedSquare;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.game.api.PlayerSymbol;
import nl.robinthedev.tictactoe.game.api.SquareToMark;
import nl.robinthedev.tictactoe.game.api.StartingPlayer;
import nl.robinthedev.tictactoe.game.api.events.GameEndedInDraw;
import nl.robinthedev.tictactoe.game.api.events.GameFinished;
import nl.robinthedev.tictactoe.game.api.events.MarkSquareRejectedGameIsOver;
import nl.robinthedev.tictactoe.game.api.events.MarkSquareRejectedNotThePlayersTurn;
import nl.robinthedev.tictactoe.game.api.events.MarkSquareRejectedSquareAlreadyTaken;
import nl.robinthedev.tictactoe.game.api.events.NewGameStarted;
import nl.robinthedev.tictactoe.game.api.events.SquareMarked;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.ResultValidator;
import org.axonframework.test.aggregate.TestExecutor;

class TicTacToeTestFixture {
  GameId gameId = new GameId(UUID.fromString("f1a7e49e-7467-429b-8bb8-9189fa39c9ec"));
  PlayerId john = new PlayerId(UUID.fromString("ace08fc9-3b81-437d-9840-53abecdf0f0b"));
  PlayerId annabel = new PlayerId(UUID.fromString("bcd8d6e1-2180-4f0d-b9d4-3389feacc402"));

  AggregateTestFixture<TicTacToeGame> ticTacToeGame;

  public TicTacToeTestFixture() {
    ticTacToeGame = new AggregateTestFixture<>(TicTacToeGame.class);
  }

  ResultValidator<TicTacToeGame> when(Object command) {
    return ticTacToeGame.when(command);
  }

  TestExecutor<TicTacToeGame> given(Object... domainEvents) {
    return ticTacToeGame.given(domainEvents);
  }

  NewGameStarted newGameStartedEvent() {
    return newGameStartedEvent(StartingPlayer.X);
  }

  NewGameStarted newGameStartedEvent(StartingPlayer startingPlayer) {
    return new NewGameStarted(gameId, john, annabel, startingPlayer);
  }

  public SquareMarked squareMarkedByJohnEvent(SquareToMark squareToMark, Grid resultingGrid) {
    return new SquareMarked(
        gameId,
        MarkedSquare.of(squareToMark, PlayerSymbol.X),
        resultingGrid.toNewGridState(),
        annabel);
  }

  public SquareMarked squareMarkedByAnnabelEvent(SquareToMark squareToMark, Grid resultingGrid) {
    return new SquareMarked(
        gameId,
        MarkedSquare.of(squareToMark, PlayerSymbol.O),
        resultingGrid.toNewGridState(),
        john);
  }

  public MarkSquareRejectedNotThePlayersTurn itsNotAnnabelsTurnEvent(Grid grid) {
    return new MarkSquareRejectedNotThePlayersTurn(gameId, john, annabel, grid.toNewGridState());
  }

  public MarkSquareRejectedSquareAlreadyTaken squareIsAlreadyMarkedEvent(Grid grid) {
    return new MarkSquareRejectedSquareAlreadyTaken(gameId, annabel, grid.toNewGridState());
  }

  public GameFinished gameWonByJohnEvent() {
    return new GameFinished(gameId, PlayerSymbol.X, john, annabel);
  }

  public GameEndedInDraw gameEndedInDrawEvent() {
    return new GameEndedInDraw(gameId, john, annabel);
  }

  public MarkSquareRejectedGameIsOver gameIsOverEvent() {
    return new MarkSquareRejectedGameIsOver(gameId, john, Grid.empty().toNewGridState());
  }
}
