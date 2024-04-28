package nl.robinthedev.tictactoe.game.events;

import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.Player;

public record MarkSquareRejectedNotThePlayersTurn(
    GameId gameId, Player currentPlayer, Player violatingPlayer) {}
