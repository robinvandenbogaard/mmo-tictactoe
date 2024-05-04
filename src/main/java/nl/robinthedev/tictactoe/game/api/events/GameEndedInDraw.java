package nl.robinthedev.tictactoe.game.api.events;

import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import org.axonframework.serialization.Revision;

@Revision("2")
public record GameEndedInDraw(GameId gameId, PlayerId playerX, PlayerId playerO)
    implements TicTacToeEvent {}
