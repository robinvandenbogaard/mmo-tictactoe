package nl.robinthedev.tictactoe.bot;

import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.NewGridState;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.game.api.commands.MarkSquare;

class Bot {

  private final PlayerId playerId;
  private final String name;
  private final MoveStrategy strategy;

  Bot(PlayerId playerId, String name, MoveStrategy strategy) {
    this.playerId = playerId;
    this.name = name;
    this.strategy = strategy;
  }

  public MarkSquare makeMove(GameId gameId, NewGridState newGridState) {
    var square = strategy.findSquareToMark(newGridState);
    return new MarkSquare(gameId, playerId, square);
  }

  public PlayerId getPlayerId() {
    return playerId;
  }

  public String getName() {
    return name;
  }
}
