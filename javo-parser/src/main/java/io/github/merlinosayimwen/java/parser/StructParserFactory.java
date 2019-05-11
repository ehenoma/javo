// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.java.parser;

import io.github.merlinosayimwen.java.parser.internal.InternalAccess;
import java.util.function.Supplier;

/**
 * The {@code {@link StructParserFactory}} provides instances of the {@code {@link StructParser}} by
 * using the {@code {@link InternalAccess}} class to get an supply of {@code {@link StructParser}}
 * implementations. This is done, so that no {@code internal} class is accessed by the client that
 * uses the library.
 *
 * @see StructParser
 * @see InternalAccess
 * @since 1.0
 */
public final class StructParserFactory {

  /** Supplier that provides implementations of the {@code {@link StructParser}} class. */
  private static Supplier<StructParser> supplier;

  static {
    StructParserFactory.supplier = InternalAccess.internalStructParserSupply();
  }

  /** Private No-Args constructor that prevents instantiation by using a constructor. */
  private StructParserFactory() {}

  /**
   * Creates a new instance of the StructParserFactory.
   *
   * @return Instance of the factory.
   */
  public static StructParserFactory create() {
    return new StructParserFactory();
  }

  /**
   * Resolves the instance of a {@code {@link StructParser}} implementation.
   *
   * <p>The {@code return-value} is most probably not created every time that this method is invoked
   * since the implementations are stateless.
   *
   * @return Instance of a {@code {@link StructParser}} implementation.
   */
  public StructParser getInstance() {
    return StructParserFactory.supplier.get();
  }
}
