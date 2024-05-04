package nl.robinthedev.tictactoe.messaging.game;

import java.util.List;

public record NewGridState(List<SquareSymbol> squares) {}
