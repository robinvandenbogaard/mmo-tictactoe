package nl.robinthedev.tictactoe.game;

import java.util.UUID;
import nl.robinthedev.tictactoe.game.events.MarkSquareRejectedNotThePlayersTurn;
import nl.robinthedev.tictactoe.game.events.NewGameStarted;
import nl.robinthedev.tictactoe.game.events.SquareMarked;
import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.MarkedSquare;
import nl.robinthedev.tictactoe.game.model.NewGridState;
import nl.robinthedev.tictactoe.game.model.PlayerId;
import nl.robinthedev.tictactoe.game.model.StartingPlayer;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.ResultValidator;
import org.axonframework.test.aggregate.TestExecutor;

class TicTacToeTestFixture {
  static final UUID GAME_UUID = UUID.fromString("f1a7e49e-7467-429b-8bb8-9189fa39c9ec");
  GameId gameId = new GameId(GAME_UUID);

  static final UUID JOHN_UUID = UUID.fromString("ace08fc9-3b81-437d-9840-53abecdf0f0b");
  PlayerId john = new PlayerId(JOHN_UUID);

  static final UUID ANNABEL_UUID = UUID.fromString("bcd8d6e1-2180-4f0d-b9d4-3389feacc402");
  PlayerId annabel = new PlayerId(ANNABEL_UUID);

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

  NewGameStarted newGameStarted() {
    return new NewGameStarted(gameId, john, annabel, StartingPlayer.X);
  }

  public SquareMarked squareMarkedByJohn(MarkedSquare markedSquare, NewGridState newGridState) {
    return new SquareMarked(gameId, markedSquare, newGridState, annabel);
  }

  public MarkSquareRejectedNotThePlayersTurn itsNotAnnabelsTurnEvent() {
    return new MarkSquareRejectedNotThePlayersTurn(gameId, john, annabel);
  }
}
