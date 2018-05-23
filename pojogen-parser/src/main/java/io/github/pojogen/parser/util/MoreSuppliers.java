package io.github.pojogen.parser.util;

import java.util.function.Supplier;

public final class MoreSuppliers {

  private MoreSuppliers() {
  }

  public static <E> Supplier<E> supplyNull() {
    return () -> null;
  }

}
