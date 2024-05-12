package nl.robinthedev.tictactoe.rating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
class PlayerRatings {
  private final Map<RankeeId, Rating> ratings;

  PlayerRatings() {
    ratings = new HashMap<>();
  }

  public Rating get(RankeeId rankeeId) {
    return ratings.computeIfAbsent(
        rankeeId, (id) -> Rating.fresh(new Rankee(id, "anonymous", false)));
  }

  public void update(Rating rating) {
    ratings.replace(rating.id(), rating);
  }

  public List<Rating> getAll() {
    return ratings.values().stream().toList();
  }
}
