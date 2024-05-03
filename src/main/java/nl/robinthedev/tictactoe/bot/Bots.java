package nl.robinthedev.tictactoe.bot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import nl.robinthedev.tictactoe.game.model.PlayerId;
import org.springframework.stereotype.Service;

@Service
class Bots {
  private static final Random RANDOM = new Random();

  private final Map<PlayerId, Bot> bots;

  public Bots() {
    this.bots = new HashMap<>();
    findOrCreate(new PlayerId(UUID.fromString("df78433d-72b0-4a9d-83b3-ef5aa021443b")), "John");
    findOrCreate(new PlayerId(UUID.fromString("2be1d7ed-2f87-4b9f-9b41-e777b57dca6a")), "Annabel");
    findOrCreate(new PlayerId(UUID.fromString("a9dfd8b2-dd64-441b-aa30-749d7b0124b8")), "Mozart");
    findOrCreate(
        new PlayerId(UUID.fromString("a2315108-0423-4ef5-9b92-8261104e3022")), "CheeseBro");
    findOrCreate(
        new PlayerId(UUID.fromString("d97d7862-34d3-4946-afae-d9fe419fcc23")), "JessyBell");
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
