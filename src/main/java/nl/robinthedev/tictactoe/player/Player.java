package nl.robinthedev.tictactoe.player;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import nl.robinthedev.tictactoe.player.commands.CreateAccount;
import nl.robinthedev.tictactoe.player.commands.UpdateUserName;
import nl.robinthedev.tictactoe.player.events.AccountCreated;
import nl.robinthedev.tictactoe.player.events.UsernameUpdated;
import nl.robinthedev.tictactoe.player.model.PlayerId;
import nl.robinthedev.tictactoe.player.model.Username;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
class Player {

  @AggregateIdentifier PlayerId playerId;
  Username username;

  public Player() {
    // Required by Axon Framework
  }

  @CommandHandler
  @CreationPolicy(AggregateCreationPolicy.ALWAYS)
  void on(CreateAccount command) {
    apply(new AccountCreated(command.playerId(), command.username()));
  }

  @EventSourcingHandler
  void handle(AccountCreated event) {
    this.playerId = event.playerId();
    this.username = event.username();
  }

  @CommandHandler
  void on(UpdateUserName command) {
    apply(new UsernameUpdated(command.playerId(), command.desiredUsername(), username));
  }

  @EventSourcingHandler
  void handle(UsernameUpdated event) {
    this.username = event.newUsername();
  }
}
