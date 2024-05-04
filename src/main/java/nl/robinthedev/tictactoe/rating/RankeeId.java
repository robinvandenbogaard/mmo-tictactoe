package nl.robinthedev.tictactoe.rating;

import java.util.UUID;
import nl.robinthedev.tictactoe.messaging.game.PlayerId;
import nl.robinthedev.tictactoe.player.model.AccountId;

record RankeeId(UUID uuid) {
  public static RankeeId of(PlayerId playerId) {
    return new RankeeId(playerId.id());
  }

  public static RankeeId of(AccountId accountId) {
    return new RankeeId(accountId.id());
  }
}
