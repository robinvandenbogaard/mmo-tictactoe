package nl.robinthedev.tictactoe.bot;

import nl.robinthedev.tictactoe.game.model.NewGridState;
import nl.robinthedev.tictactoe.game.model.SquareToMark;

interface MoveStrategy {
  SquareToMark findSquareToMark(NewGridState newGridState);
}
