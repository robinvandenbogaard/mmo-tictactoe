package nl.robinthedev.tictactoe.messaging.game.events;

import nl.robinthedev.tictactoe.messaging.game.GameId;
import nl.robinthedev.tictactoe.messaging.game.PlayerId;
import org.axonframework.serialization.Revision;

@Revision("2")
public record GameEndedInDraw(GameId gameId, PlayerId playerX, PlayerId playerO)
    implements TicTacToeEvent {}
