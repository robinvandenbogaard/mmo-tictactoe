package nl.robinthedev.tictactoe;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TicTacToeMmoApplicationTests.AxonTestConfig.class)
class TicTacToeMmoApplicationTests {

  @Test
  void contextLoads() {}

  @TestConfiguration
  static class AxonTestConfig {
    @Bean
    public static EventBus createSpyEventBus() {
      return EmbeddedEventStore.builder().storageEngine(new InMemoryEventStorageEngine()).build();
    }
  }
}
