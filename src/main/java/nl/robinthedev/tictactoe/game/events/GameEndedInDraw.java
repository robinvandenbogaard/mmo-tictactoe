package nl.robinthedev.tictactoe.game.events;

import nl.robinthedev.tictactoe.game.model.GameId;
import nl.robinthedev.tictactoe.game.model.PlayerId;
import org.axonframework.serialization.Revision;

@Revision("2")
public record GameEndedInDraw(GameId gameId, PlayerId playerX, PlayerId playerO)
    implements TicTacToeEvent {}
