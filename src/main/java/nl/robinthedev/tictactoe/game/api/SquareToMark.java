package nl.robinthedev.tictactoe.game.api;

public record SquareToMark(int column, int row) {

  public static final SquareToMark TOP_LEFT = SquareToMark.of(0, 0);
  public static final SquareToMark TOP_CENTER = SquareToMark.of(1, 0);
  public static final SquareToMark TOP_RIGHT = SquareToMark.of(2, 0);
  public static final SquareToMark MIDDLE_LEFT = SquareToMark.of(0, 1);
  public static final SquareToMark MIDDLE_CENTER = SquareToMark.of(1, 1);
  public static final SquareToMark MIDDLE_RIGHT = SquareToMark.of(2, 1);
  public static final SquareToMark BOTTOM_LEFT = SquareToMark.of(0, 2);
  public static final SquareToMark BOTTOM_CENTER = SquareToMark.of(1, 2);
  public static final SquareToMark BOTTOM_RIGHT = SquareToMark.of(2, 2);

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

  public static SquareToMark fromIndex(int index) {
    var column = index % 3;
    var row = index / 3;
    return new SquareToMark(column, row);
  }
}
