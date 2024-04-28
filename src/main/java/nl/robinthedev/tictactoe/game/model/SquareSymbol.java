package nl.robinthedev.tictactoe.game.model;

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
}
