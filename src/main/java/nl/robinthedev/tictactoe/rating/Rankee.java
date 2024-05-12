package nl.robinthedev.tictactoe.rating;

record Rankee(RankeeId id, String username, boolean isHuman) {
  public Rankee asHuman() {
    return new Rankee(id, username, true);
  }

  public Rankee named(String username) {
    return new Rankee(id, username, isHuman);
  }
}
