package nl.robinthedev.tictactoe.game.events;

import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.MarkedSquare;
import nl.robinthedev.tictactoe.game.model.NewGridState;

public record SquareMarked(
    GameId id,
    MarkedSquare markedSquare,
    NewGridState gridState,
    nl.robinthedev.tictactoe.game.model.Player nextPlayer) {}
