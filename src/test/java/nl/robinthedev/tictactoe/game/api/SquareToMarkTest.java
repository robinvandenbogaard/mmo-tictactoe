package nl.robinthedev.tictactoe.game.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SquareToMarkTest {

  @ParameterizedTest
  @MethodSource("indices")
  void fromIndex(int index, SquareToMark expected) {
    assertThat(SquareToMark.fromIndex(index)).isEqualTo(expected);
  }

  public static Stream<Arguments> indices() {
    return Stream.of(
        Arguments.of(0, SquareToMark.TOP_LEFT),
        Arguments.of(1, SquareToMark.TOP_CENTER),
        Arguments.of(2, SquareToMark.TOP_RIGHT),
        Arguments.of(3, SquareToMark.MIDDLE_LEFT),
        Arguments.of(4, SquareToMark.MIDDLE_CENTER),
        Arguments.of(5, SquareToMark.MIDDLE_RIGHT),
        Arguments.of(6, SquareToMark.BOTTOM_LEFT),
        Arguments.of(7, SquareToMark.BOTTOM_CENTER),
        Arguments.of(8, SquareToMark.BOTTOM_RIGHT));
  }
}
