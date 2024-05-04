package nl.robinthedev.tictactoe.rating;

record Summary(int total, Ratio win, Ratio loss, Ratio draw) {
  public static Summary of(int total, int win, int draw, int loss) {
    return new Summary(total, ratio(total, win), ratio(total, loss), ratio(total, draw));
  }

  private static Ratio ratio(double total, int value) {
    var ratio = value / total;
    return new Ratio(value, ratio);
  }
}
