package nl.robinthedev.tictactoe.rating;

record Rankee(RankeeId id, boolean isHuman) {
  public Rankee asHuman() {
    return new Rankee(id, true);
  }
}
