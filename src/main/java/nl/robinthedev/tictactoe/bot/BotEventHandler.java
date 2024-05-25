package nl.robinthedev.tictactoe.bot;

import nl.robinthedev.tictactoe.botaccount.events.BotAccountCreated;
import nl.robinthedev.tictactoe.game.api.PlayerId;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("BotEventHandler")
class BotEventHandler {

  private static final Logger log = LoggerFactory.getLogger(BotEventHandler.class);

  private final Bots bots;

  public BotEventHandler(Bots bots) {
    this.bots = bots;
  }

  @EventHandler
  void handle(BotAccountCreated event) {
    log.trace("{}", event);
    bots.create(new PlayerId(event.accountId().id()), event.username().username());
  }
}
