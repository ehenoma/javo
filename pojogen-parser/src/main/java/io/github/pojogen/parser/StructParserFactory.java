package io.github.pojogen.parser;

import java.util.function.Supplier;

import io.github.pojogen.parser.internal.InternalAccess;

/**
 * The {@code {@link StructParserFactory}} provides instances of the {@code {@link StructParser}} by
 * using the {@code {@link InternalAccess}} class to get an supply of {@code {@link StructParser}}
 * implementations. This is done, so that no {@code internal} class is accessed by the client that
 * uses the library.
 *
 * @author Merlin Osayimwen
 * @see StructParser
 * @see InternalAccess
 * @since 1.0
 */
public final class StructParserFactory {

  /**
   * Supplier that provides implementations of the {@code {@link StructParser}} class.
   */
  private static Supplier<StructParser> supplier;

  static {
    StructParserFactory.supplier = InternalAccess.internalStructParserSupply();
  }

  /**
   * Resolves the instance of a {@code {@link StructParser}} implementation.
   *
   * The {@code return-value} is most probably not created every time that this method is invoked
   * since the implementations are stateless.
   *
   * @return Instance of a {@code {@link StructParser}} implementation.
   */
  public StructParser getInstance() {
    return StructParserFactory.supplier.get();
  }

}
