package nl.robinthedev.tictactoe.rating;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RatingEndpoint {

  private final PlayerRatings ratings;

  public RatingEndpoint(PlayerRatings ratings) {
    this.ratings = ratings;
  }

  @GetMapping("ranking")
  List<Ranking> ranking() {
    return ratings.getAll().stream()
        .map(this::toRanking)
        .sorted()
        .map(WithIndex.indexed())
        .map(this::updateRank)
        .toList();
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
