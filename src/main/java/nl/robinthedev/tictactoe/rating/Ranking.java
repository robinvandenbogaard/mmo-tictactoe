package nl.robinthedev.tictactoe.rating;

import java.util.Comparator;

record Ranking(Rankee rankee, int rank, Summary summary) implements Comparable<Ranking> {

  @Override
  public int compareTo(Ranking other) {
    return Comparator.comparing(Ratio::ratio)
        .reversed()
        .compare(summary.win(), other.summary.win());
  }

  Ranking setRank(int rank) {
    return new Ranking(rankee, rank, summary);
  }

  public boolean belongsTo(RankeeId rankeeId) {
    return rankee.id().equals(rankeeId);
  }
}
