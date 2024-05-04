package nl.robinthedev.tictactoe.game;

import static nl.robinthedev.tictactoe.messaging.game.SquareSymbol.EMPTY;
import static nl.robinthedev.tictactoe.messaging.game.SquareSymbol.O;
import static nl.robinthedev.tictactoe.messaging.game.SquareSymbol.X;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import nl.robinthedev.tictactoe.messaging.game.PlayerSymbol;
import nl.robinthedev.tictactoe.messaging.game.SquareToMark;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GridTest {

  @Test
  void buildsEmptyGrid() {
    assertThat(Grid.fromString("-,-,-,-,-,-,-,-,-")).isEqualTo(Grid.empty());
  }

  @Test
  void buildsFullXGrid() {
    assertThat(Grid.fromString("x,x,x,x,x,x,x,x,x"))
        .isEqualTo(new Grid(List.of(X, X, X, X, X, X, X, X, X)));
  }

  @Test
  void buildsFullOGrid() {
    assertThat(Grid.fromString("o,o,o,o,o,o,o,o,o"))
        .isEqualTo(new Grid(List.of(O, O, O, O, O, O, O, O, O)));
  }

  @Test
  void buildsMixedGrid() {
    assertThat(Grid.fromString("o,x,-,x,-,o,-,x,o"))
        .isEqualTo(new Grid(List.of(O, X, EMPTY, X, EMPTY, O, EMPTY, X, O)));
  }

  @Test
  void cannotDealWithMoreThanNineFields() {
    assertThatThrownBy(() -> Grid.fromString("x,x,x,x,x,x,x,x,x,x"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void cannotDealWithLessThanNineFields() {
    assertThatThrownBy(() -> Grid.fromString("x,x,x,x,x,x,x,x"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @ParameterizedTest
  @MethodSource("allPositionsX")
  void markSquareX(SquareToMark squareToMark, Grid expectedGrid) {
    var grid = Grid.empty();
    var result = grid.markSquare(new Move(squareToMark, PlayerSymbol.X));

    assertThat(result).isEqualTo(expectedGrid);
  }

  public static Stream<Arguments> allPositionsX() {
    return Stream.of(
        Arguments.of(SquareToMark.TOP_LEFT, Grid.fromString("x,-,-,-,-,-,-,-,-")),
        Arguments.of(SquareToMark.TOP_CENTER, Grid.fromString("-,x,-,-,-,-,-,-,-")),
        Arguments.of(SquareToMark.TOP_RIGHT, Grid.fromString("-,-,x,-,-,-,-,-,-")),
        Arguments.of(SquareToMark.MIDDLE_LEFT, Grid.fromString("-,-,-,x,-,-,-,-,-")),
        Arguments.of(SquareToMark.MIDDLE_CENTER, Grid.fromString("-,-,-,-,x,-,-,-,-")),
        Arguments.of(SquareToMark.MIDDLE_RIGHT, Grid.fromString("-,-,-,-,-,x,-,-,-")),
        Arguments.of(SquareToMark.BOTTOM_LEFT, Grid.fromString("-,-,-,-,-,-,x,-,-")),
        Arguments.of(SquareToMark.BOTTOM_CENTER, Grid.fromString("-,-,-,-,-,-,-,x,-")),
        Arguments.of(SquareToMark.BOTTOM_RIGHT, Grid.fromString("-,-,-,-,-,-,-,-,x")));
  }

  @ParameterizedTest
  @MethodSource("allPositionsO")
  void markSquareO(SquareToMark squareToMark, Grid expectedGrid) {
    var grid = Grid.empty();
    var result = grid.markSquare(new Move(squareToMark, PlayerSymbol.O));

    assertThat(result).isEqualTo(expectedGrid);
  }

  public static Stream<Arguments> allPositionsO() {
    return Stream.of(
        Arguments.of(SquareToMark.TOP_LEFT, Grid.fromString("o,-,-,-,-,-,-,-,-")),
        Arguments.of(SquareToMark.TOP_CENTER, Grid.fromString("-,o,-,-,-,-,-,-,-")),
        Arguments.of(SquareToMark.TOP_RIGHT, Grid.fromString("-,-,o,-,-,-,-,-,-")),
        Arguments.of(SquareToMark.MIDDLE_LEFT, Grid.fromString("-,-,-,o,-,-,-,-,-")),
        Arguments.of(SquareToMark.MIDDLE_CENTER, Grid.fromString("-,-,-,-,o,-,-,-,-")),
        Arguments.of(SquareToMark.MIDDLE_RIGHT, Grid.fromString("-,-,-,-,-,o,-,-,-")),
        Arguments.of(SquareToMark.BOTTOM_LEFT, Grid.fromString("-,-,-,-,-,-,o,-,-")),
        Arguments.of(SquareToMark.BOTTOM_CENTER, Grid.fromString("-,-,-,-,-,-,-,o,-")),
        Arguments.of(SquareToMark.BOTTOM_RIGHT, Grid.fromString("-,-,-,-,-,-,-,-,o")));
  }
}
