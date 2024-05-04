package nl.robinthedev.tictactoe.game.api;

public enum SquareSymbol {
  X,
  O,
  EMPTY;

  public static SquareSymbol fromInput(String input) {
    return switch (input) {
      case "x" -> X;
      case "o" -> O;
      case "-" -> EMPTY;
      default -> throw new IllegalStateException("Unexpected value: " + input);
    };
  }

  public boolean isEmpty() {
    return this.equals(EMPTY);
  }
}
