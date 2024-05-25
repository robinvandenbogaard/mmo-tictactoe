package nl.robinthedev.tictactoe.rating;

import java.util.HashMap;
import java.util.List;
import nl.robinthedev.tictactoe.util.templating.PebbelTemplate;
import org.springframework.stereotype.Service;

@Service
class PebbleRatingTemplating {

  public String personalRanking(Ranking ranking) {
    HashMap<String, Object> context = new HashMap<>();
    context.put("rank", ranking.rank());
    context.put("name", ranking.rankee().username());
    context.put("win", ranking.summary().win().count());
    context.put("loss", ranking.summary().loss().count());
    context.put("draw", ranking.summary().draw().count());
    return PebbelTemplate.processTemplate("personalRanking", context);
  }

  public String rankingList(List<Ranking> rankings) {
    HashMap<String, Object> context = new HashMap<>();
    context.put("rankings", rankings);
    return PebbelTemplate.processTemplate("rankingList", context);
  }

}
