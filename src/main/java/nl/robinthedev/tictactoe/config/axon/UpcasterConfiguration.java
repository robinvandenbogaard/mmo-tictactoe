package nl.robinthedev.tictactoe.config.axon;

import nl.robinthedev.tictactoe.game.events.GameEndedInDraw;
import org.axonframework.serialization.upcasting.event.EventTypeUpcaster;
import org.axonframework.serialization.upcasting.event.EventUpcasterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpcasterConfiguration {

  private static final Logger log = LoggerFactory.getLogger(UpcasterConfiguration.class);

  public UpcasterConfiguration() {
    log.info("UpcasterConfiguration initialized");
  }

  @Bean
  public EventUpcasterChain eventUpcasterChain() {
    return new EventUpcasterChain(
        EventTypeUpcaster.from("nl.robinthedev.tictactoe.game.events.GameDraw", null)
            .to(GameEndedInDraw.class, "2"));
  }
}
