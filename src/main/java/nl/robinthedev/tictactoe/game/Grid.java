package nl.robinthedev.tictactoe.game;

import static nl.robinthedev.tictactoe.game.model.SquareSymbol.EMPTY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import nl.robinthedev.tictactoe.game.MarkResult.GameFinished;
import nl.robinthedev.tictactoe.game.MarkResult.SquareAlreadyMarked;
import nl.robinthedev.tictactoe.game.MarkResult.ValidMarking;
import nl.robinthedev.tictactoe.game.model.MarkedSquare;
import nl.robinthedev.tictactoe.game.model.NewGridState;
import nl.robinthedev.tictactoe.game.model.PlayerSymbol;
import nl.robinthedev.tictactoe.game.model.SquareSymbol;
import nl.robinthedev.tictactoe.game.model.SquareToMark;

record Grid(List<SquareSymbol> squares) {

  public static final int GRID_SIZE = 3;
  public static final int REQUIRED_SQUARES = GRID_SIZE * GRID_SIZE;

  Grid {
    if (squares.size() != REQUIRED_SQUARES) {
      throw new IllegalArgumentException(
          "A grid must contain %s squares. It received %s."
              .formatted(REQUIRED_SQUARES, squares.size()));
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
    return getWinningSymbol(updatedSquares)
        .map(
            winningSymbol ->
                (MarkResult)
                    new GameFinished(
                        new MarkedSquare(squareToMark.column(), squareToMark.row(), symbol),
                        new NewGridState(updatedSquares),
                        symbol))
        .orElseGet(
            () ->
                new ValidMarking(
                    new MarkedSquare(squareToMark.column(), squareToMark.row(), symbol),
                    new NewGridState(updatedSquares)));
  }

  private int getPosition(SquareToMark squareToMark) {
    return squareToMark.column() + (squareToMark.row() * GRID_SIZE);
  }

  private List<SquareSymbol> updateSquares(int position, PlayerSymbol symbol) {
    var updatedSquares = new ArrayList<>(squares);
    switch (symbol) {
      case X -> updatedSquares.set(position, SquareSymbol.X);
      case O -> updatedSquares.set(position, SquareSymbol.O);
    }
    return updatedSquares;
  }

  private Optional<PlayerSymbol> getWinningSymbol(List<SquareSymbol> updatedSquares) {
    return checkWinner(updatedSquares)
        .map(
            squareSymbol ->
                switch (squareSymbol) {
                  case X -> PlayerSymbol.X;
                  case O -> PlayerSymbol.O;
                  case EMPTY -> null;
                });
  }

  private Optional<SquareSymbol> checkWinner(List<SquareSymbol> squares) {
    // Check rows, columns, and diagonals directly
    // Rows: 0, 1, 2
    for (int i = 0; i < 3; i++) {
      SquareSymbol first = squares.get(i * 3);
      if (first != EMPTY && first == squares.get(i * 3 + 1) && first == squares.get(i * 3 + 2)) {
        return Optional.of(first);
      }
    }
    // Columns: 0, 1, 2
    for (int i = 0; i < 3; i++) {
      SquareSymbol first = squares.get(i);
      if (first != EMPTY && first == squares.get(i + 3) && first == squares.get(i + 6)) {
        return Optional.of(first);
      }
    }
    // Diagonals
    SquareSymbol center = squares.get(4);
    if (center != EMPTY
        && ((center == squares.get(0) && center == squares.get(8))
            || (center == squares.get(2) && center == squares.get(6)))) {
      return Optional.of(center);
    }

    return Optional.empty();
  }
}
