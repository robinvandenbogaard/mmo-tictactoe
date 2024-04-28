package nl.robinthedev.tictactoe.game.model;

public record SquareToMark(int column, int row) {

  public static SquareToMark of(int column, int row) {
    if (column < 0 || column > 2) {
      throw new IllegalArgumentException(
          "Column must be a value between 0 and 2, not %s".formatted(column));
    }
    if (row < 0 || row > 2) {
      throw new IllegalArgumentException(
          "Row must be a value between 0 and 2, not %s".formatted(row));
    }
    return new SquareToMark(column, row);
  }
}
