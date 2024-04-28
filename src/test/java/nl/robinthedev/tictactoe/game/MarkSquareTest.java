package nl.robinthedev.tictactoe.game;

import static nl.robinthedev.tictactoe.game.TicTacToeTestFixture.ANNABEL_UUID;
import static org.assertj.core.api.Assertions.assertThat;

import nl.robinthedev.tictactoe.game.commands.MarkSquare;
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
        .when(new MarkSquare(fixture.gameId, fixture.john, SquareToMark.of(0, 0)))
        .expectEvents(
            fixture.squareMarkedByJohn(
                MarkedSquare.of(0, 0, PlayerSymbol.X), expectedGrid.toNewGridState()))
        .expectState(game -> assertThat(game.grid).isEqualTo(expectedGrid));
  }

  @Test
  void updatesTheCurrentPlayer() {
    fixture
        .given(fixture.newGameStarted())
        .when(new MarkSquare(fixture.gameId, fixture.john, SquareToMark.of(0, 0)))
        .expectState(game -> assertThat(game.players.isPlayerTurn(ANNABEL_UUID)).isTrue());
  }
}
