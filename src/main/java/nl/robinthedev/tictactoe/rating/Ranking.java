package nl.robinthedev.tictactoe.rating;

import java.util.Comparator;
import nl.robinthedev.tictactoe.game.model.PlayerId;

record Ranking(PlayerId player, Summary summary) implements Comparable<Ranking> {

  @Override
  public int compareTo(Ranking other) {
    return Comparator.comparing(Ratio::ratio)
        .reversed()
        .compare(summary.win(), other.summary.win());
  }
}
