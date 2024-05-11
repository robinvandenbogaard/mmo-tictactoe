package nl.robinthedev.tictactoe.rating;

import java.util.UUID;
import nl.robinthedev.tictactoe.game.api.PlayerId;

record RankeeId(UUID uuid) {
  public static RankeeId of(PlayerId playerId) {
    return new RankeeId(playerId.id());
  }

  public static RankeeId of(nl.robinthedev.tictactoe.account.api.RankeeId accountId) {
    return new RankeeId(accountId.id());
  }
}
