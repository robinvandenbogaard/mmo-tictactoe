package nl.robinthedev.tictactoe.game.events;

import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.PlayerId;

public record GameDraw(GameId gameId, PlayerId playerX, PlayerId playerO)
    implements TicTacToeEvent {}
