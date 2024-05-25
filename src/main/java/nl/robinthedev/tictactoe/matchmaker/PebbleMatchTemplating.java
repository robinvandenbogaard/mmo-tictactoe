package nl.robinthedev.tictactoe.matchmaker;

import java.util.HashMap;
import nl.robinthedev.tictactoe.util.templating.PebbelTemplate;
import org.springframework.stereotype.Service;

@Service
class PebbleMatchTemplating {

  public String queueSize(int queueSize) {
    HashMap<String, Object> context = new HashMap<>();
    context.put("size", queueSize);
    return PebbelTemplate.processTemplate("queueSize", context);
  }
}
