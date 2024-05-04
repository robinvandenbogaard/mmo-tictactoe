package nl.robinthedev.tictactoe.rating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.robinthedev.tictactoe.game.model.PlayerId;
import org.springframework.stereotype.Service;

@Service
class PlayerRatings {
  private final Map<PlayerId, Rating> ratings;

  PlayerRatings() {
    ratings = new HashMap<>();
  }

  public Rating get(PlayerId playerId) {
    return ratings.computeIfAbsent(playerId, Rating::fresh);
  }

  public void update(Rating rating) {
    ratings.replace(rating.id(), rating);
  }

  public List<Rating> getAll() {
    return ratings.values().stream().toList();
  }
}
