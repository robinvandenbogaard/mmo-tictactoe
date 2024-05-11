package nl.robinthedev.tictactoe.rating;

import java.util.function.Function;

record WithIndex<T>(int index, T value) {

  @Override
  public String toString() {
    return value + "(" + index + ")";
  }

  static <T> Function<T, WithIndex<T>> indexed() {
    return new Function<T, WithIndex<T>>() {
      int index = 0;

      @Override
      public WithIndex<T> apply(T t) {
        return new WithIndex<>(index++, t);
      }
    };
  }
}
