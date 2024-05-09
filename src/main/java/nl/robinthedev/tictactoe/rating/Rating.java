package nl.robinthedev.tictactoe.rating;

record Rating(Rankee rankee, int draw, int win, int loss) {

  public static Rating fresh(Rankee rankee) {
    return new Rating(rankee, 0, 0, 0);
  }

  public Rating addDraw() {
    return new Rating(rankee, draw + 1, win, loss);
  }

  public Rating addWin() {
    return new Rating(rankee, draw, win + 1, loss);
  }

  public Rating addLoss() {
    return new Rating(rankee, draw, win, loss + 1);
  }

  public int gamesCount() {
    return draw + win + loss;
  }

  public RankeeId id() {
    return rankee().id();
  }

  public Rating markHuman() {
    return new Rating(rankee().asHuman(), draw, win, loss);
  }
}
