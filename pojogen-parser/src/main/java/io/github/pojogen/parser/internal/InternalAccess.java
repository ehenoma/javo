package io.github.pojogen.parser.internal;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import io.github.pojogen.parser.StructParser;

/**
 * The {@code {@link InternalAccess}} class provides {@code {@link Supplier suppliers}} of {@code
 * internal} library classes that can't be created from outside of the {@code internal} package.
 *
 * @author Merlin Osayimwen
 * @see InternalStructParser
 * @since 1.0
 */
public final class InternalAccess {

  /**
   * Private constructor of {@code {@link InternalAccess}} that makes the class not instantiable.
   */
  private InternalAccess() {
  }

  /**
   * This supplier is used by the {@code {@link io.github.pojogen.parser.StructParserFactory
   * StructParserFactory}} to resolve implementations of the {@code StructParser}} class.
   *
   * @return {@code {@link Supplier}} that provides a {@code {@link StructParser}} implementation.
   */
  public static Supplier<StructParser> internalStructParserSupply() {
    return Suppliers.memoize(InternalStructParser::new);
  }

}
