package nl.robinthedev.tictactoe.config;

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
    return new EventUpcasterChain();
  }
}
