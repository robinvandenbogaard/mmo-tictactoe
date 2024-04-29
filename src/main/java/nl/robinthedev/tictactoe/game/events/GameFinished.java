package nl.robinthedev.tictactoe.game.events;

import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.PlayerId;
import nl.robinthedev.tictactoe.game.model.PlayerSymbol;

public record GameFinished(
    GameId gameId, PlayerSymbol winningSymbol, PlayerId playerThatWon, PlayerId playerThatLost)
    implements TicTacToeEvent {}
