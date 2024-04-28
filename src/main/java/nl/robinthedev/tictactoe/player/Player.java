package nl.robinthedev.tictactoe.player;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import nl.robinthedev.tictactoe.player.commands.CreateAccount;
import nl.robinthedev.tictactoe.player.events.AccountCreated;
import nl.robinthedev.tictactoe.player.model.PlayerId;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
class Player {

  @AggregateIdentifier PlayerId playerId;

  public Player() {
    // Required by Axon Framework
  }

  @CommandHandler
  @CreationPolicy(AggregateCreationPolicy.ALWAYS)
  void on(CreateAccount command) {
    apply(new AccountCreated(command.playerId()));
  }

  @EventSourcingHandler
  void handle(AccountCreated event) {
    this.playerId = event.playerId();
  }
}
