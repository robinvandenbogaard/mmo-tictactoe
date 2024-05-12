package nl.robinthedev.tictactoe.rating;

import nl.robinthedev.tictactoe.account.api.events.AccountCreated;
import nl.robinthedev.tictactoe.botaccount.events.BotAccountCreated;
import nl.robinthedev.tictactoe.game.api.events.GameEndedInDraw;
import nl.robinthedev.tictactoe.game.api.events.GameFinished;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("PlayerRatingEventHandler")
class PlayerRatingEventHandler {

  private static final Logger log = LoggerFactory.getLogger(PlayerRatingEventHandler.class);
  private final PlayerRatings ratings;

  public PlayerRatingEventHandler(PlayerRatings ratings) {
    this.ratings = ratings;
    log.info("Started PlayerRatingEventHandler");
  }

  @EventHandler
  void handle(GameEndedInDraw event) {
    log.trace("{}", event);
    ratings.update(ratings.get(RankeeId.of(event.playerX())).addDraw());
    ratings.update(ratings.get(RankeeId.of(event.playerO())).addDraw());
  }

  @EventHandler
  void handle(GameFinished event) {
    log.trace("{}", event);

    ratings.update(ratings.get(RankeeId.of(event.playerThatWon())).addWin());
    ratings.update(ratings.get(RankeeId.of(event.playerThatLost())).addLoss());
  }

  @EventHandler
  void handle(AccountCreated event) {
    log.trace("{}", event);
    ratings.update(ratings.get(RankeeId.of(event.accountId())).markHuman());
  }

  @EventHandler
  void handle(BotAccountCreated event) {
    log.trace("{}", event);
    ratings.update(ratings.get(RankeeId.of(event.accountId())).named(event.username().username()));
  }
}
