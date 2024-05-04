package nl.robinthedev.tictactoe.bot;

import nl.robinthedev.tictactoe.messaging.game.NewGridState;
import nl.robinthedev.tictactoe.messaging.game.SquareToMark;

interface MoveStrategy {
  SquareToMark findSquareToMark(NewGridState newGridState);
}
