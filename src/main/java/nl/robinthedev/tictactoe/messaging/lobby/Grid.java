package nl.robinthedev.tictactoe.messaging.lobby;

import java.util.List;

public record Grid(List<Mark> grid) {
  public static Grid empty() {
    return new Grid(
        List.of(
            Mark.EMPTY,
            Mark.EMPTY,
            Mark.EMPTY,
            Mark.EMPTY,
            Mark.EMPTY,
            Mark.EMPTY,
            Mark.EMPTY,
            Mark.EMPTY,
            Mark.EMPTY));
  }
}
