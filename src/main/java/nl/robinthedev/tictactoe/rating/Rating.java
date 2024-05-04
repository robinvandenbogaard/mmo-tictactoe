package nl.robinthedev.tictactoe.rating;

import nl.robinthedev.tictactoe.game.model.PlayerId;

record Rating(PlayerId id, int draw, int win, int loss) {

  public static Rating fresh(PlayerId id) {
    return new Rating(id, 0, 0, 0);
  }

  public Rating addDraw() {
    return new Rating(id, draw + 1, win, loss);
  }

  public Rating addWin() {
    return new Rating(id, draw, win + 1, loss);
  }

  public Rating addLoss() {
    return new Rating(id, draw, win, loss + 1);
  }

  public int gamesCount() {
    return draw + win + loss;
  }
}
