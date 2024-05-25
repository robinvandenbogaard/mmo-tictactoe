package nl.robinthedev.tictactoe.util.templating;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PebbelTemplate {
  public static String processTemplate(String template, Map<String, Object> context) {
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
