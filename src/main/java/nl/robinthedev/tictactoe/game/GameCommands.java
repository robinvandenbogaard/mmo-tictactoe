package nl.robinthedev.tictactoe.game;

import java.util.concurrent.ExecutionException;
import nl.robinthedev.tictactoe.account.api.AccountId;
import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.game.api.SquareToMark;
import nl.robinthedev.tictactoe.game.api.commands.MarkSquare;
import nl.robinthedev.tictactoe.matchmaker.api.QueueResult;
import nl.robinthedev.tictactoe.matchmaker.api.RequestGame;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class GameCommands {

  private static final Logger log = LoggerFactory.getLogger(GameCommands.class);
  private final CommandGateway commandGateway;
  private final QueryGateway queryGateway;

  public GameCommands(CommandGateway commandGateway, QueryGateway queryGateway) {
    this.commandGateway = commandGateway;
    this.queryGateway = queryGateway;
  }

  void requestNewGame(AccountId accountId) {
    try {
      queryGateway.query(new RequestGame(accountId), QueueResult.class).get();
    } catch (InterruptedException | ExecutionException e) {
      log.error("Could not request new game", e);
    }
  }

  void markSquare(GameId gameId, PlayerId player, SquareToMark square) {
    commandGateway.send(new MarkSquare(gameId, player, square));
  }
}
