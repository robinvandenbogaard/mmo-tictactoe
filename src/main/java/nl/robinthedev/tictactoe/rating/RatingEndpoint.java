package nl.robinthedev.tictactoe.rating;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RatingEndpoint {

  private final PlayerRatings ratings;
  private final PebbleRatingTemplating templating;

  public RatingEndpoint(PlayerRatings ratings, PebbleRatingTemplating templating) {
    this.ratings = ratings;
    this.templating = templating;
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

  @GetMapping(value = "ranking", produces = "text/html")
  String rankingHtml() {
    List<Ranking> rankings =
        ratings.getAll().stream()
            .map(this::toRanking)
            .sorted()
            .map(WithIndex.indexed())
            .map(this::updateRank)
            .toList();

    return templating.rankingList(rankings);
  }

  @GetMapping(value = "ranking/account", produces = "text/html")
  String accountRankingHtml(@CookieValue(name = "accountId") UUID accountId) {
    Ranking ranking =
        ratings.getAll().stream()
            .map(this::toRanking)
            .sorted()
            .map(WithIndex.indexed())
            .map(this::updateRank)
            .filter(anyRanking -> anyRanking.belongsTo(new RankeeId(accountId)))
            .findFirst()
            .orElseThrow();

    return templating.personalRanking(ranking);
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
