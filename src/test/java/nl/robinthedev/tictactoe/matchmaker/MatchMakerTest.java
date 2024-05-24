package nl.robinthedev.tictactoe.matchmaker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import nl.robinthedev.tictactoe.account.api.AccountId;
import nl.robinthedev.tictactoe.game.api.commands.StartNewGame;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class MatchMakerTest {

  public static final AccountId ACCOUNT_1 = new AccountId(UUID.randomUUID());
  public static final AccountId ACCOUNT_2 = new AccountId(UUID.randomUUID());

  private CommandGateway commands;
  private MatchMaker mm;

  @BeforeEach
  void setUp() {
    commands = mock();
    mm = new MatchMaker(commands);
  }

  @Test
  void addsAccountToQueue() {
    var result = mm.handle(new RequestGame(ACCOUNT_1));

    assertThat(result).isEqualTo(new AddedToQueue(1));
  }

  @Test
  void addsMultipleAccountsToQueue() {
    mm.handle(new RequestGame(ACCOUNT_1));

    var result = mm.handle(new RequestGame(ACCOUNT_2));

    assertThat(result).isEqualTo(new AddedToQueue(2));
  }

  @Test
  void addsMultipleSameAccountsToQueue() {
    mm.handle(new RequestGame(ACCOUNT_1));

    var result = mm.handle(new RequestGame(ACCOUNT_1));

    assertThat(result).isEqualTo(new AddedToQueue(2));
  }

  @Test
  void doesNotMakeAGameIfNotEnoughPlayers() {
    mm.handle(new RequestGame(ACCOUNT_1));

    mm.findMatchAndCreateGame();

    verifyNoInteractions(commands);
  }

  @Test
  void matchesTwoDifferentAccounts() {
    var captor = ArgumentCaptor.forClass(StartNewGame.class);

    mm.handle(new RequestGame(ACCOUNT_1));
    mm.handle(new RequestGame(ACCOUNT_2));

    mm.findMatchAndCreateGame();

    verify(commands).send(captor.capture());
    var command = captor.getValue();

    assertThat(command.playerX().id()).isIn(ACCOUNT_1.id(), ACCOUNT_2.id());
    assertThat(command.playerO().id()).isIn(ACCOUNT_1.id(), ACCOUNT_2.id());
    assertThat(command.playerX()).isNotEqualTo(command.playerO());
  }

  @Test
  void removesMatchedAccountsFromQueue() {
    mm.handle(new RequestGame(ACCOUNT_1));
    mm.handle(new RequestGame(ACCOUNT_2));
    mm.findMatchAndCreateGame();

    var result = mm.handle(new RequestGame(ACCOUNT_1));
    assertThat(result).isEqualTo(new AddedToQueue(1));
  }

  @Test
  void onlyRemovesOneMatchedAccountFromQueue() {
    mm.handle(new RequestGame(ACCOUNT_1));
    mm.handle(new RequestGame(ACCOUNT_1));
    mm.handle(new RequestGame(ACCOUNT_2));
    mm.findMatchAndCreateGame();

    var result = mm.handle(new RequestGame(ACCOUNT_1));
    assertThat(result).isEqualTo(new AddedToQueue(2));
  }

  @Test
  void willNotMatchSameAccount() {
    mm.handle(new RequestGame(ACCOUNT_1));
    mm.handle(new RequestGame(ACCOUNT_1));

    mm.findMatchAndCreateGame();

    verifyNoInteractions(commands);
  }

  @Test
  void oneAccountCannotQueueMoreThanFiveTimes() {
    mm.handle(new RequestGame(ACCOUNT_1));
    mm.handle(new RequestGame(ACCOUNT_1));
    mm.handle(new RequestGame(ACCOUNT_1));
    mm.handle(new RequestGame(ACCOUNT_1));
    mm.handle(new RequestGame(ACCOUNT_1));

    var result = mm.handle(new RequestGame(ACCOUNT_1));

    assertThat(result).isInstanceOf(MaximumQueueEntriesExceeded.class);
  }
}
