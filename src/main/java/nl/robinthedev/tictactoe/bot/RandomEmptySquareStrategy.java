package nl.robinthedev.tictactoe.bot;

import java.util.Random;
import nl.robinthedev.tictactoe.game.api.NewGridState;
import nl.robinthedev.tictactoe.game.api.SquareToMark;

class RandomEmptySquareStrategy implements MoveStrategy {

  private static final Random RANDOM = new Random();

  @Override
  public SquareToMark findSquareToMark(NewGridState newGridState) {
    int index;
    do {
      index = RANDOM.nextInt(newGridState.squares().size());
    } while (!newGridState.squares().get(index).isEmpty());

    return SquareToMark.fromIndex(index);
  }
}
