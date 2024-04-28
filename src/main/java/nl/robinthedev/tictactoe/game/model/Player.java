package nl.robinthedev.tictactoe.game.model;

import java.util.UUID;

public record Player(UUID ref) {
  public static Player fromString(String uuid) {
    return new Player(UUID.fromString(uuid));
  }
}
