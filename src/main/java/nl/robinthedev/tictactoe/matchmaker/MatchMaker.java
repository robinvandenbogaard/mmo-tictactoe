package nl.robinthedev.tictactoe.matchmaker;

import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import nl.robinthedev.tictactoe.account.api.AccountId;
import nl.robinthedev.tictactoe.game.api.GameId;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import nl.robinthedev.tictactoe.game.api.commands.StartNewGame;
import nl.robinthedev.tictactoe.matchmaker.api.GetQueueSize;
import nl.robinthedev.tictactoe.matchmaker.api.QueueResult;
import nl.robinthedev.tictactoe.matchmaker.api.QueueSize;
import nl.robinthedev.tictactoe.matchmaker.api.RequestGame;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
class MatchMaker {
  private static final Logger log = LoggerFactory.getLogger(MatchMaker.class);

  private final CommandGateway commands;
  private final Queue<AccountId> queue;

  MatchMaker(CommandGateway commands) {
    this.commands = commands;
    this.queue = new ConcurrentLinkedQueue<>();
  }

  @QueryHandler
  QueueResult handle(RequestGame query) {
    if (queue.stream().filter(query.accountId()::equals).count() >= 5) return QueueResult.maxed();

    log.debug("Adding account {} to queue ", query.accountId());
    queue.add(query.accountId());
    return QueueResult.added(queue.size());
  }

  @QueryHandler
  QueueSize handle(GetQueueSize query) {
    return new QueueSize(queue.size());
  }

  @Scheduled(initialDelay = 5_000, fixedDelay = 1_000)
  void findMatchAndCreateGame() {
    log.trace("Attempting to find match and create game with a queue of {}", queue.size());
    // Create a Set to track unique AccountID records
    Set<AccountId> uniqueSet = new HashSet<>();

    // Use Stream API to find the first two unique AccountID records
    List<AccountId> uniqueAccountIDs =
        queue.stream()
            .filter(uniqueSet::add) // Add to the set and filter unique items
            .limit(2)
            .toList();
    log.trace("Found {} unique accounts", uniqueAccountIDs.size());

    if (uniqueAccountIDs.size() == 2) {
      // Remove only the first occurrence of each found unique AccountID record from the queue
      for (var uniqueId : uniqueAccountIDs) {
        queue.remove(uniqueId);
        log.trace("Removed unique account {} from queue", uniqueId);
      }

      var match = uniqueSet.stream().toList();
      var playerX = new PlayerId(match.get(0).id());
      var playerO = new PlayerId(match.get(1).id());
      log.debug("Found match for {} and {}. Queuesize is now {}", playerX, playerO, queue.size());
      var command = new StartNewGame(new GameId(UUID.randomUUID()), playerX, playerO);
      commands.send(command);
    }
  }
}
