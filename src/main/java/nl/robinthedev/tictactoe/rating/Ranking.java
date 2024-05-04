package nl.robinthedev.tictactoe.rating;

import java.util.Comparator;

record Ranking(Rankee rankee, Summary summary) implements Comparable<Ranking> {

  @Override
  public int compareTo(Ranking other) {
    return Comparator.comparing(Ratio::ratio)
        .reversed()
        .compare(summary.win(), other.summary.win());
  }
}
