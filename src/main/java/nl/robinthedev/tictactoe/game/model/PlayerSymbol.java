package nl.robinthedev.tictactoe.game.model;

public enum PlayerSymbol {
  X,
  O;

  public PlayerSymbol other() {

    return switch (this) {
      case X -> O;
      case O -> X;
    };
  }
}
