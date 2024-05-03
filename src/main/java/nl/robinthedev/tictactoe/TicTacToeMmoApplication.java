package nl.robinthedev.tictactoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TicTacToeMmoApplication {

  public static void main(String[] args) {
    SpringApplication.run(TicTacToeMmoApplication.class, args);
  }
}
