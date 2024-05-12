package nl.robinthedev.tictactoe.rating;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PebbleTemplating {

  public String personalRanking(Ranking ranking) {
    HashMap<String, Object> context = new HashMap<>();
    context.put("rank", ranking.rank());
    context.put("name", ranking.rankee().username());
    context.put("win", ranking.summary().win().count());
    context.put("loss", ranking.summary().loss().count());
    context.put("draw", ranking.summary().draw().count());
    return processTemplate("personalRanking", context);
  }

  private static String processTemplate(String template, Map<String, Object> context) {
    PebbleEngine engine = new PebbleEngine.Builder().build();
    PebbleTemplate compiledTemplate = engine.getTemplate("templates/" + template + ".html");

    Writer writer = new StringWriter();
    try {
      compiledTemplate.evaluate(writer, context);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return writer.toString();
  }
}
