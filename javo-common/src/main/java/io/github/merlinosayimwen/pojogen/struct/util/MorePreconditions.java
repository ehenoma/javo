package io.github.merlinosayimwen.pojogen.struct.util;

import com.google.common.base.Preconditions;

public final class MorePreconditions {
  private MorePreconditions() {}

  public static void checkAllNotNull(Iterable<?> elements) {
    Preconditions.checkNotNull(elements);
    elements.forEach(Preconditions::checkNotNull);
  }
}
