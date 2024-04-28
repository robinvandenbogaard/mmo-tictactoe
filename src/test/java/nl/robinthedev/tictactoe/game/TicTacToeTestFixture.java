package nl.robinthedev.tictactoe.game;

import nl.robinthedev.tictactoe.game.events.NewGameStarted;
import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.Player;
import nl.robinthedev.tictactoe.game.model.StartingPlayer;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.ResultValidator;

class TicTacToeTestFixture {
  static final String GAME_UUID = "f1a7e49e-7467-429b-8bb8-9189fa39c9ec";
  GameId gameId = GameId.fromString(GAME_UUID);

  static final String JOHN_UUID = "ace08fc9-3b81-437d-9840-53abecdf0f0b";
  Player john = Player.fromString(JOHN_UUID);

  static final String ANNABEL_UUID = "bcd8d6e1-2180-4f0d-b9d4-3389feacc402";
  Player annabel = Player.fromString(ANNABEL_UUID);

  AggregateTestFixture<TicTacToeGame> ticTacToeGame;

  public TicTacToeTestFixture() {
    ticTacToeGame = new AggregateTestFixture<>(TicTacToeGame.class);
  }

  ResultValidator<TicTacToeGame> when(Object command) {
    return ticTacToeGame.when(command);
  }

  NewGameStarted newGameStarted() {
    return new NewGameStarted(gameId, john, annabel, StartingPlayer.X);
  }
}
