package nl.robinthedev.tictactoe.account;

import java.util.UUID;
import nl.robinthedev.tictactoe.account.api.RankeeId;
import nl.robinthedev.tictactoe.account.api.Username;
import nl.robinthedev.tictactoe.account.api.events.AccountCreated;
import nl.robinthedev.tictactoe.account.api.events.UsernameUpdated;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.ResultValidator;
import org.axonframework.test.aggregate.TestExecutor;

class PlayerTestFixture {

  private static final UUID PLAYER_UUID = UUID.fromString("cb174089-0a41-4a38-a4c6-a5b4a361d1b3");
  RankeeId accountId = new RankeeId(PLAYER_UUID);
  Username firstGenUsername = new Username("user_1261231829");
  Username chosenUsername = new Username("Tic Tac Toe Master");

  AggregateTestFixture<UserAccount> ticTacToeGame;

  public PlayerTestFixture() {
    ticTacToeGame = new AggregateTestFixture<>(UserAccount.class);
  }

  ResultValidator<UserAccount> when(Object command) {
    return ticTacToeGame.when(command);
  }

  TestExecutor<UserAccount> given(Object... domainEvents) {
    return ticTacToeGame.given(domainEvents);
  }

  public AccountCreated accountCreatedEvent() {
    return new AccountCreated(accountId, firstGenUsername);
  }

  public UsernameUpdated usernameUpdatedEvent() {
    return new UsernameUpdated(accountId, chosenUsername, firstGenUsername);
  }
}
