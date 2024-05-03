package nl.robinthedev.tictactoe.games.model;

import static nl.robinthedev.tictactoe.games.model.Mark.EMPTY;

import java.util.List;

public record Grid(List<Mark> grid) {
  public static Grid empty() {
    return new Grid(List.of(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY));
  }
}
