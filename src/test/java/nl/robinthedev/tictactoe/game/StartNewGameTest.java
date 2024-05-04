package nl.robinthedev.tictactoe.game;

import static org.assertj.core.api.Assertions.assertThat;

import nl.robinthedev.tictactoe.game.api.commands.StartNewGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StartNewGameTest {

  TicTacToeTestFixture fixture;

  @BeforeEach
  void setUp() {
    fixture = new TicTacToeTestFixture();
  }

  @Test
  void startsNewGame() {
    fixture
        .when(new StartNewGame(fixture.gameId, fixture.john, fixture.annabel))
        .expectEvents(fixture.newGameStartedEvent())
        .expectState(game -> assertThat(game.gameId).isEqualTo(fixture.gameId));
  }

  @Test
  void updatesAggregateId() {
    fixture
        .when(new StartNewGame(fixture.gameId, fixture.john, fixture.annabel))
        .expectState(game -> assertThat(game.gameId).isEqualTo(fixture.gameId));
  }

  @Test
  void startsWithABlankGrid() {
    fixture
        .when(new StartNewGame(fixture.gameId, fixture.john, fixture.annabel))
        .expectState(game -> assertThat(game.grid).isEqualTo(Grid.empty()));
  }

  @Test
  void johnStartsBecauseHeIsPlayerX() {
    fixture
        .when(new StartNewGame(fixture.gameId, fixture.john, fixture.annabel))
        .expectState(game -> assertThat(game.players.isPlayerTurn(fixture.john)).isTrue());
  }

  @Test
  void annabelStartsBecauseSheIsPlayerX() {
    fixture
        .when(new StartNewGame(fixture.gameId, fixture.annabel, fixture.john))
        .expectState(game -> assertThat(game.players.isPlayerTurn(fixture.annabel)).isTrue());
  }
}
