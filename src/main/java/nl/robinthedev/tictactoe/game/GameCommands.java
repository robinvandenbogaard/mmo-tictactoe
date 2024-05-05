package nl.robinthedev.tictactoe.game;

import java.util.UUID;
import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.game.api.SquareToMark;
import nl.robinthedev.tictactoe.game.api.commands.MarkSquare;
import nl.robinthedev.tictactoe.game.api.commands.StartNewGame;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

@Service
class GameCommands {

  private final CommandGateway commandGateway;

  public GameCommands(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  void startNewGame(PlayerId playerX, PlayerId playerO) {
    commandGateway.send(new StartNewGame(new GameId(UUID.randomUUID()), playerX, playerO));
  }

  void markSquare(GameId gameId, PlayerId player, SquareToMark square) {
    commandGateway.send(new MarkSquare(gameId, player, square));
  }
}
