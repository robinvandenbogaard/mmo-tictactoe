package nl.robinthedev.tictactoe.game;

import static org.assertj.core.api.Assertions.assertThat;

import nl.robinthedev.tictactoe.game.model.MarkedSquare;
import nl.robinthedev.tictactoe.game.model.PlayerSymbol;
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
  void marksTheSquare() {
    var expectedGrid = Grid.fromString("x,-,-,-,-,-,-,-,-");

    fixture
        .given(fixture.newGameStarted())
        .when(fixture.johnMarksTopLeft())
        .expectEvents(
            fixture.squareMarkedByJohn(
                MarkedSquare.of(SquareToMark.TOP_LEFT, PlayerSymbol.X),
                expectedGrid.toNewGridState()))
        .expectState(game -> assertThat(game.grid).isEqualTo(expectedGrid));
  }

  @Test
  void updatesTheCurrentPlayerToAnnabel() {
    fixture
        .given(fixture.newGameStarted())
        .when(fixture.johnMarksTopLeft())
        .expectState(game -> assertThat(game.players.isPlayerTurn(fixture.annabel)).isTrue());
  }

  @Test
  void annabelCannotMarkASquareInJohnsTurn() {
    fixture
        .given(fixture.newGameStarted())
        .when(fixture.annabelMarksTopLeft())
        .expectEvents(fixture.itsNotAnnabelsTurnEvent())
        .expectState(game -> assertThat(game.players.isPlayerTurn(fixture.john)).isTrue());
  }

  @Test
  void annabelCannotMarkASquareThatJohnAlreadyMarked() {
    var expectedGrid = Grid.fromString("x,-,-,-,-,-,-,-,-");
    var initialState = new TicTacToeGame();
    initialState.gameId = fixture.gameId;
    initialState.grid = expectedGrid;
    initialState.players = fixture.annabelsTurn();

    fixture
        .givenState(() -> initialState)
        .when(fixture.annabelMarksTopLeft())
        .expectEvents(fixture.squareIsAlreadyMarkedEvent())
        .expectState(game -> assertThat(game.players.isPlayerTurn(fixture.annabel)).isTrue());
  }
}
