package nl.robinthedev.tictactoe.player;

import java.util.UUID;
import nl.robinthedev.tictactoe.player.events.AccountCreated;
import nl.robinthedev.tictactoe.player.events.UsernameUpdated;
import nl.robinthedev.tictactoe.player.model.PlayerId;
import nl.robinthedev.tictactoe.player.model.Username;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.ResultValidator;
import org.axonframework.test.aggregate.TestExecutor;

class PlayerTestFixture {

  private static final UUID PLAYER_UUID = UUID.fromString("cb174089-0a41-4a38-a4c6-a5b4a361d1b3");
  PlayerId playerId = new PlayerId(PLAYER_UUID);
  Username firstGenUsername = new Username("user_1261231829");
  Username chosenUsername = new Username("Tic Tac Toe Master");

  AggregateTestFixture<Player> ticTacToeGame;

  public PlayerTestFixture() {
    ticTacToeGame = new AggregateTestFixture<>(Player.class);
  }

  ResultValidator<Player> when(Object command) {
    return ticTacToeGame.when(command);
  }

  TestExecutor<Player> given(Object... domainEvents) {
    return ticTacToeGame.given(domainEvents);
  }

  public AccountCreated accountCreatedEvent() {
    return new AccountCreated(playerId, firstGenUsername);
  }

  public UsernameUpdated usernameUpdatedEvent() {
    return new UsernameUpdated(playerId, chosenUsername, firstGenUsername);
  }
}
