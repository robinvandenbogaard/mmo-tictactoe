package nl.robinthedev.tictactoe.rating;

import java.util.UUID;
import nl.robinthedev.tictactoe.account.api.AccountId;
import nl.robinthedev.tictactoe.game.api.PlayerId;

record RankeeId(UUID uuid) {
  public static RankeeId of(PlayerId playerId) {
    return new RankeeId(playerId.id());
  }

  public static RankeeId of(AccountId accountId) {
    return new RankeeId(accountId.id());
  }
}
