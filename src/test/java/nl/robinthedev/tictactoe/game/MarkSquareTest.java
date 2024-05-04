package nl.robinthedev.tictactoe.game;

import static org.assertj.core.api.Assertions.assertThat;

import nl.robinthedev.tictactoe.messaging.game.commands.MarkSquare;
import nl.robinthedev.tictactoe.messaging.game.SquareToMark;
import nl.robinthedev.tictactoe.messaging.game.StartingPlayer;
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
        .expectEvents(fixture.itsNotAnnabelsTurnEvent(Grid.empty()));
  }

  @Test
  void annabelCannotMarkASquareThatJohnAlreadyMarked() {
    fixture
        .given(fixture.newGameStartedEvent())
        .andGiven(
            fixture.squareMarkedByJohnEvent(
                SquareToMark.TOP_LEFT, Grid.fromString("x,-,-,-,-,-,-,-,-")))
        .when(new MarkSquare(fixture.gameId, fixture.annabel, SquareToMark.TOP_LEFT))
        .expectEvents(fixture.squareIsAlreadyMarkedEvent(Grid.fromString("x,-,-,-,-,-,-,-,-")));
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
  void gameEndedWithWinnerEvents() {
    fixture
        .given(fixture.newGameStartedEvent(StartingPlayer.O))
        .andGiven(
            fixture.squareMarkedByAnnabelEvent(
                SquareToMark.TOP_CENTER, Grid.fromString("x,o,-,x,o,-,-,-,-")))
        .when(new MarkSquare(fixture.gameId, fixture.john, SquareToMark.BOTTOM_LEFT))
        .expectEvents(
            fixture.squareMarkedByJohnEvent(
                SquareToMark.BOTTOM_LEFT, Grid.fromString("x,o,-,x,o,-,x,-,-")),
            fixture.gameWonByJohnEvent());
  }

  @Test
  void gameEndedInDraw() {
    fixture
        .given(fixture.newGameStartedEvent(StartingPlayer.O))
        .andGiven(
            fixture.squareMarkedByAnnabelEvent(
                SquareToMark.TOP_CENTER, Grid.fromString("x,x,o,o,o,x,x,o,-")))
        .when(new MarkSquare(fixture.gameId, fixture.john, SquareToMark.BOTTOM_RIGHT))
        .expectEvents(
            fixture.squareMarkedByJohnEvent(
                SquareToMark.BOTTOM_RIGHT, Grid.fromString("x,x,o,o,o,x,x,o,x")),
            fixture.gameEndedInDrawEvent());
  }

  @Test
  void cannotMarkASquareOnAFinishedGameInDraw() {
    fixture
        .given(fixture.newGameStartedEvent(StartingPlayer.O))
        .andGiven(fixture.gameEndedInDrawEvent())
        .when(new MarkSquare(fixture.gameId, fixture.john, SquareToMark.BOTTOM_RIGHT))
        .expectEvents(fixture.gameIsOverEvent());
  }

  @Test
  void cannotMarkASquareOnAFinishedGameWithWinner() {
    fixture
        .given(fixture.newGameStartedEvent(StartingPlayer.O))
        .andGiven(fixture.gameWonByJohnEvent())
        .when(new MarkSquare(fixture.gameId, fixture.john, SquareToMark.BOTTOM_RIGHT))
        .expectEvents(fixture.gameIsOverEvent());
  }
}
