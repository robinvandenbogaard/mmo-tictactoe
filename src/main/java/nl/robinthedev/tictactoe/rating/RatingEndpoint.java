package nl.robinthedev.tictactoe.rating;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RatingEndpoint {

  private final PlayerRatings ratings;

  public RatingEndpoint(PlayerRatings ratings) {
    this.ratings = ratings;
  }

  @GetMapping(value = "ranking", produces = "application/json")
  List<Ranking> ranking() {
    return ratings.getAll().stream()
        .map(this::toRanking)
        .sorted()
        .map(WithIndex.indexed())
        .map(this::updateRank)
        .toList();
  }

  @GetMapping(value = "ranking/{rankeeId}", produces = "application/json")
  Ranking accountRanking(@PathVariable UUID rankeeId) {
    return ratings.getAll().stream()
        .map(this::toRanking)
        .sorted()
        .map(WithIndex.indexed())
        .map(this::updateRank)
        .filter(ranking -> ranking.belongsTo(new RankeeId(rankeeId)))
        .findFirst()
        .orElseThrow();
  }

  private Ranking toRanking(Rating rating) {
    var total = rating.gamesCount();
    return new Ranking(
        rating.rankee(), -1, Summary.of(total, rating.win(), rating.draw(), rating.loss()));
  }

  private Ranking updateRank(WithIndex<Ranking> indexedRanking) {
    return indexedRanking.value().setRank(indexedRanking.index() + 1);
  }
}
