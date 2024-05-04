package nl.robinthedev.tictactoe.messaging.game.events;

import nl.robinthedev.tictactoe.messaging.game.GameId;
import nl.robinthedev.tictactoe.messaging.game.PlayerId;
import nl.robinthedev.tictactoe.messaging.game.PlayerSymbol;

public record GameFinished(
        GameId gameId, PlayerSymbol winningSymbol, PlayerId playerThatWon, PlayerId playerThatLost)
    implements TicTacToeEvent {}
