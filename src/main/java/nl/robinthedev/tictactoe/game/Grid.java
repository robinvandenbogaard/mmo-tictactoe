package nl.robinthedev.tictactoe.game;

import static nl.robinthedev.tictactoe.game.model.SquareSymbol.EMPTY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.robinthedev.tictactoe.game.MarkResult.SquareAlreadyMarked;
import nl.robinthedev.tictactoe.game.MarkResult.ValidMarking;
import nl.robinthedev.tictactoe.game.model.MarkedSquare;
import nl.robinthedev.tictactoe.game.model.NewGridState;
import nl.robinthedev.tictactoe.game.model.PlayerSymbol;
import nl.robinthedev.tictactoe.game.model.SquareSymbol;
import nl.robinthedev.tictactoe.game.model.SquareToMark;

record Grid(List<SquareSymbol> squares) {
  Grid {
    if (squares.size() != 9) {
      throw new IllegalArgumentException(
          "A grid must contain 9 squares. It received %s.".formatted(squares.size()));
    }
  }

  public static Grid empty() {
    return new Grid(List.of(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY));
  }

  public static Grid fromString(String input) {
    var grid = Arrays.stream(input.split(",")).map(SquareSymbol::fromInput).toList();
    return new Grid(grid);
  }

  public static Grid from(NewGridState newGridState) {
    return new Grid(newGridState.squares());
  }

  public NewGridState toNewGridState() {
    return new NewGridState(squares);
  }

  public MarkResult markSquare(SquareToMark squareToMark, PlayerSymbol symbol) {
    var position = getPosition(squareToMark);
    if (!squares.get(position).equals(EMPTY)) {
      return new SquareAlreadyMarked();
    }

    var updatedSquares = updateSquares(position, symbol);
    return new ValidMarking(
        new MarkedSquare(squareToMark.column(), squareToMark.row(), symbol),
        new NewGridState(updatedSquares));
  }

  private List<SquareSymbol> updateSquares(int position, PlayerSymbol symbol) {
    var updatedSquares = new ArrayList<>(squares);
    switch (symbol) {
      case X -> updatedSquares.set(position, SquareSymbol.X);
      case O -> updatedSquares.set(position, SquareSymbol.O);
    }
    return updatedSquares;
  }

  private int getPosition(SquareToMark squareToMark) {
    return squareToMark.column() + (squareToMark.row() * squareToMark.column());
  }
}
