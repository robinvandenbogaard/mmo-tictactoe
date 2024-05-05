package nl.robinthedev.tictactoe.lobby.api;

import java.util.List;

public record Grid(List<Mark> cells) {
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
