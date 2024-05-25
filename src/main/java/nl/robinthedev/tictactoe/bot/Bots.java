package nl.robinthedev.tictactoe.bot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import org.springframework.stereotype.Service;

@Service
class Bots {
  private final Map<PlayerId, Bot> bots;

  public Bots() {
    this.bots = new HashMap<>();
  }

  Bot findOrCreate(PlayerId playerId, String name) {
    return bots.computeIfAbsent(
        playerId, (key) -> new Bot(key, name, new RandomEmptySquareStrategy()));
  }

  public List<Bot> allBots() {
    return bots.values().stream().toList();
  }

  public boolean doesNotExist(PlayerId playerId) {
    return bots.containsKey(playerId);
  }
}
