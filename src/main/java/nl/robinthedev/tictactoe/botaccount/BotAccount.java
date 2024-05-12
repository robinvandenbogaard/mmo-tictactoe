package nl.robinthedev.tictactoe.botaccount;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import nl.robinthedev.tictactoe.account.api.AccountId;
import nl.robinthedev.tictactoe.account.api.Username;
import nl.robinthedev.tictactoe.botaccount.commands.CreateBotAccount;
import nl.robinthedev.tictactoe.botaccount.events.BotAccountCreated;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
class BotAccount {

  @AggregateIdentifier AccountId accountId;
  Username username;

  public BotAccount() {
    // Required by Axon Framework
  }

  @CommandHandler
  @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
  void on(CreateBotAccount command) {
    apply(new BotAccountCreated(command.accountId(), command.username()));
  }

  @EventSourcingHandler
  void handle(BotAccountCreated event) {
    this.accountId = event.accountId();
    this.username = event.username();
  }
}
