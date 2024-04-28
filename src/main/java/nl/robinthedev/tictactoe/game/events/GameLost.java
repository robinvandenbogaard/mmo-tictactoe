package nl.robinthedev.tictactoe.game.events;

import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.PlayerId;

public record GameLost(GameId gameId, PlayerId playerThatLost) implements TicTacToeEvent {}
