package nl.robinthedev.tictactoe.messaging.game;

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
