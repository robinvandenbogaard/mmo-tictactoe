package nl.robinthedev.tictactoe.account;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import nl.robinthedev.tictactoe.account.api.AccountId;
import nl.robinthedev.tictactoe.account.api.Username;
import nl.robinthedev.tictactoe.account.api.commands.CreateAccount;
import nl.robinthedev.tictactoe.account.api.commands.UpdateUserName;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AccountEndpoint {

  private final CommandGateway commandGateway;

  AccountEndpoint(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @PostMapping(value = "account/new/{name}")
  void ranking(@PathVariable String name, HttpServletResponse response) {
    var accountId = UUID.randomUUID();
    commandGateway.send(new CreateAccount(new AccountId(accountId), new Username(name)));

    Cookie cookie = new Cookie("accountId", accountId.toString());
    cookie.setMaxAge(-1);
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  @PostMapping(value = "account/edit/{name}")
  void ranking(@CookieValue(name = "accountId") UUID accountId, @PathVariable String name) {
    commandGateway.send(new UpdateUserName(new AccountId(accountId), new Username(name)));
  }
}
