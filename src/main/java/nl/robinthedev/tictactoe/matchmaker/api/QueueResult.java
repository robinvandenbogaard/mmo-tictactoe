package nl.robinthedev.tictactoe.matchmaker.api;

public sealed interface QueueResult permits AddedToQueue, MaximumQueueEntriesExceeded {
  static QueueResult maxed() {
    return new MaximumQueueEntriesExceeded();
  }

  static QueueResult added(int queueSize) {
    return new AddedToQueue(queueSize);
  }
}
