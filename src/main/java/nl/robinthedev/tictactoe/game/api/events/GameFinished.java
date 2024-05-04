package nl.robinthedev.tictactoe.game.api.events;

import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.game.api.PlayerSymbol;

public record GameFinished(
        GameId gameId, PlayerSymbol winningSymbol, PlayerId playerThatWon, PlayerId playerThatLost)
    implements TicTacToeEvent {}
