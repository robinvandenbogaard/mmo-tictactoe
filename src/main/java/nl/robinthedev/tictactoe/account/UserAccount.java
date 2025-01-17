package nl.robinthedev.tictactoe.account;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import nl.robinthedev.tictactoe.account.api.AccountId;
import nl.robinthedev.tictactoe.account.api.Username;
import nl.robinthedev.tictactoe.account.api.commands.CreateAccount;
import nl.robinthedev.tictactoe.account.api.commands.UpdateUserName;
import nl.robinthedev.tictactoe.account.api.events.AccountCreated;
import nl.robinthedev.tictactoe.account.api.events.UsernameUpdated;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
class UserAccount {

  @AggregateIdentifier AccountId accountId;
  Username username;

  public UserAccount() {
    // Required by Axon Framework
  }

  @CommandHandler
  @CreationPolicy(AggregateCreationPolicy.ALWAYS)
  void on(CreateAccount command) {
    apply(new AccountCreated(command.accountId(), command.username()));
  }

  @EventSourcingHandler
  void handle(AccountCreated event) {
    this.accountId = event.accountId();
    this.username = event.username();
  }

  @CommandHandler
  void on(UpdateUserName command) {
    apply(new UsernameUpdated(command.accountId(), command.desiredUsername(), username));
  }

  @EventSourcingHandler
  void handle(UsernameUpdated event) {
    this.username = event.newUsername();
  }
}
