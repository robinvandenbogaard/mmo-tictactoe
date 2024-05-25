package nl.robinthedev.tictactoe.matchmaker;

import java.util.concurrent.ExecutionException;
import nl.robinthedev.tictactoe.matchmaker.api.GetQueueSize;
import nl.robinthedev.tictactoe.matchmaker.api.QueueSize;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class QueueEndpoint {

  private final PebbleMatchTemplating templating;
  private final QueryGateway queryGateway;

  public QueueEndpoint(PebbleMatchTemplating templating, QueryGateway queryGateway) {
    this.templating = templating;
    this.queryGateway = queryGateway;
  }

  @GetMapping(value = "/queue/size", produces = "text/html")
  String getQueueSize() throws ExecutionException, InterruptedException {
    var size = queryGateway.query(new GetQueueSize(), QueueSize.class).get();
    return templating.queueSize(size.queueSize());
  }
}
