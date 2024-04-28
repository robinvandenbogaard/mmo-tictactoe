package nl.robinthedev.tictactoe.game;

import static org.assertj.core.api.Assertions.assertThat;

import nl.robinthedev.tictactoe.game.commands.MarkSquare;
import nl.robinthedev.tictactoe.game.model.SquareToMark;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MarkSquareTest {

  TicTacToeTestFixture fixture;

  @BeforeEach
  void setUp() {
    fixture = new TicTacToeTestFixture();
  }

  @Test
  void dispatchesAnSquareMarkedEvent() {
    fixture
        .given(fixture.newGameStartedEvent())
        .when(new MarkSquare(fixture.gameId, fixture.john, SquareToMark.TOP_LEFT))
        .expectEvents(
            fixture.squareMarkedByJohnEvent(
                SquareToMark.TOP_LEFT, Grid.fromString("x,-,-,-,-,-,-,-,-")));
  }

  @Test
  void updatesTheGrid() {
    fixture
        .given(fixture.newGameStartedEvent())
        .when(new MarkSquare(fixture.gameId, fixture.john, SquareToMark.TOP_LEFT))
        .expectState(game -> assertThat(game.grid).isEqualTo(Grid.fromString("x,-,-,-,-,-,-,-,-")));
  }

  @Test
  void updatesTheCurrentPlayerToAnnabel() {
    fixture
        .given(fixture.newGameStartedEvent())
        .when(new MarkSquare(fixture.gameId, fixture.john, SquareToMark.TOP_LEFT))
        .expectState(game -> assertThat(game.players.isPlayerTurn(fixture.annabel)).isTrue());
  }

  @Test
  void annabelCannotMarkASquareInJohnsTurn() {
    fixture
        .given(fixture.newGameStartedEvent())
        .when(new MarkSquare(fixture.gameId, fixture.annabel, SquareToMark.TOP_LEFT))
        .expectEvents(fixture.itsNotAnnabelsTurnEvent());
  }

  @Test
  void annabelCannotMarkASquareThatJohnAlreadyMarked() {
    fixture
        .given(fixture.newGameStartedEvent())
        .andGiven(
            fixture.squareMarkedByJohnEvent(
                SquareToMark.TOP_LEFT, Grid.fromString("x,-,-,-,-,-,-,-,-")))
        .when(new MarkSquare(fixture.gameId, fixture.annabel, SquareToMark.TOP_LEFT))
        .expectEvents(fixture.squareIsAlreadyMarkedEvent());
  }

  @Test
  void annabelCanMarkAnEmptySquare() {
    fixture
        .given(fixture.newGameStartedEvent())
        .andGiven(
            fixture.squareMarkedByJohnEvent(
                SquareToMark.TOP_LEFT, Grid.fromString("x,-,-,-,-,-,-,-,-")))
        .when(new MarkSquare(fixture.gameId, fixture.annabel, SquareToMark.MIDDLE_CENTER))
        .expectEvents(
            fixture.squareMarkedByAnnabelEvent(
                SquareToMark.MIDDLE_CENTER, Grid.fromString("x,-,-,-,o,-,-,-,-")));
  }

  @Test
  void gameEndedEvents() {
    fixture
        .given(fixture.newGameStartedEvent())
        .andGiven(
            fixture.squareMarkedByAnnabelEvent(
                SquareToMark.TOP_CENTER, Grid.fromString("x,o,-,x,o,-,-,-,-")))
        .when(new MarkSquare(fixture.gameId, fixture.john, SquareToMark.BOTTOM_LEFT))
        .expectEvents(
            fixture.squareMarkedByJohnEvent(
                SquareToMark.BOTTOM_LEFT, Grid.fromString("x,o,-,x,o,-,x,-,-")),
            fixture.gameWonByJohnEvent(),
            fixture.gameLostByAnnabelEvent());
  }
}
