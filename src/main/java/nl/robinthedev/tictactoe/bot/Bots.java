package nl.robinthedev.tictactoe.bot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import org.springframework.stereotype.Service;

@Service
class Bots {
  private static final Random RANDOM = new Random();

  private final Map<PlayerId, Bot> bots;

  public Bots() {
    this.bots = new HashMap<>();
  }

  Bot findOrCreate(PlayerId playerId, String name) {
    return bots.computeIfAbsent(
        playerId, (key) -> new Bot(key, name, new RandomEmptySquareStrategy()));
  }

  public PlayerId anyOtherBot(PlayerId other) {
    int index;
    var botList = bots.keySet().stream().toList();

    do {
      index = RANDOM.nextInt(botList.size());
    } while (botList.get(index).equals(other));

    return botList.get(index);
  }

  public List<Bot> allBots() {
    return bots.values().stream().toList();
  }
}
