package nl.robinthedev.tictactoe.player;

import static org.assertj.core.api.Assertions.assertThat;

import nl.robinthedev.tictactoe.player.commands.CreateAccount;
import nl.robinthedev.tictactoe.player.commands.UpdateUserName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserAccountTest {

  PlayerTestFixture fixture;

  @BeforeEach
  void setUp() {
    fixture = new PlayerTestFixture();
  }

  @Test
  void createsAccount() {
    fixture
        .when(new CreateAccount(fixture.accountId, fixture.firstGenUsername))
        .expectEvents(fixture.accountCreatedEvent())
        .expectState(
            player -> {
              assertThat(player.accountId).isEqualTo(fixture.accountId);
              assertThat(player.username).isEqualTo(fixture.firstGenUsername);
            });
  }

  @Test
  void canUpdateUsername() {
    fixture
        .given(fixture.accountCreatedEvent())
        .when(new UpdateUserName(fixture.accountId, fixture.chosenUsername))
        .expectEvents(fixture.usernameUpdatedEvent())
        .expectState(player -> assertThat(player.username).isEqualTo(fixture.chosenUsername));
  }
}
