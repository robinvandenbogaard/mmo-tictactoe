package nl.robinthedev.tictactoe.bot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class Bots {
  private static final Logger log = LoggerFactory.getLogger(Bots.class);
  private final Map<PlayerId, Bot> bots;

  public Bots() {
    this.bots = new HashMap<>();
  }

  void create(PlayerId playerId, String name) {
    log.trace("Creating bot {}. We now have {} bots", playerId, bots.size());
    bots.put(playerId, new Bot(playerId, name, new RandomEmptySquareStrategy()));
  }

  Bot find(PlayerId playerId) {
    return bots.get(playerId);
  }

  public List<Bot> allBots() {
    return bots.values().stream().toList();
  }

  public boolean doesNotExist(PlayerId playerId) {
    return bots.containsKey(playerId);
  }
}
