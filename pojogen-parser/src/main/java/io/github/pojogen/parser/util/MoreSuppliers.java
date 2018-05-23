package io.github.pojogen.parser.util;

import java.util.function.Supplier;

/**
 * Provides additional factory methods that help working with the {@code {@link Supplier}} class.
 * This class is more of an complement to {@code Guavas} utility-class.
 *
 * @author Merlin Osayimwen
 * @see Supplier
 * @since 1.0
 */
public final class MoreSuppliers {

  /**
   * Private constructor that prohibits construction of the utility-class.
   */
  private MoreSuppliers() {
  }

  /**
   * Returns a supplier of the given type that always supplies {@code null}.
   *
   * @param <E> Type of the {@code {@link Supplier}}.
   * @return Supplier of the given type that always supplies {@code null}.
   */
  public static <E> Supplier<E> supplyNull() {
    return () -> null;
  }

}
