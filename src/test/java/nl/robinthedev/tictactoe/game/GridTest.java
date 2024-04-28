package nl.robinthedev.tictactoe.game;

import static nl.robinthedev.tictactoe.game.model.SquareSymbol.EMPTY;
import static nl.robinthedev.tictactoe.game.model.SquareSymbol.O;
import static nl.robinthedev.tictactoe.game.model.SquareSymbol.X;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

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
    assertThat(Grid.fromString("o,x,-,o,o,o,o,o,o"))
        .isEqualTo(new Grid(List.of(O, X, EMPTY, O, O, O, O, O, O)));
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
}
