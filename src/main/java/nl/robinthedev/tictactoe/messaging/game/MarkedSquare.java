package nl.robinthedev.tictactoe.messaging.game;

public record MarkedSquare(int column, int row, PlayerSymbol symbol) {
  public static MarkedSquare of(SquareToMark squareToMark, PlayerSymbol symbol) {
    var column = squareToMark.column();
    var row = squareToMark.row();
    if (column < 0 || column > 2) {
      throw new IllegalArgumentException(
          "Column must be a value between 0 and 2, not %s".formatted(column));
    }
    if (row < 0 || row > 2) {
      throw new IllegalArgumentException(
          "Row must be a value between 0 and 2, not %s".formatted(row));
    }
    return new MarkedSquare(column, row, symbol);
  }
}
