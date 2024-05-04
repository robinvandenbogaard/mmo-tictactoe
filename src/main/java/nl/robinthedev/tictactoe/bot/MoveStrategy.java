package nl.robinthedev.tictactoe.bot;

import nl.robinthedev.tictactoe.game.api.NewGridState;
import nl.robinthedev.tictactoe.game.api.SquareToMark;

interface MoveStrategy {
  SquareToMark findSquareToMark(NewGridState newGridState);
}
