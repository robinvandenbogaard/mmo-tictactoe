package nl.robinthedev.tictactoe.messaging.game;

import java.util.UUID;

public record GameId(UUID id) {
  public static GameId fromString(String uuid) {
    return new GameId(UUID.fromString(uuid));
  }
}
