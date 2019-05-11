// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.java.parser.internal;

import com.google.common.base.Suppliers;
import io.github.merlinosayimwen.java.parser.StructParser;
import io.github.merlinosayimwen.java.parser.StructParserFactory;

import java.util.function.Supplier;

/**
 * The {@code {@link InternalAccess}} class provides {@code {@link Supplier suppliers}} of {@code
 * internal} library classes that can't be created from outside of the {@code internal} package.
 *
 * @see InternalStructParser
 * @since 1.0
 */
public final class InternalAccess {

  /**
   * Private constructor of {@code {@link InternalAccess}} that makes the class not instantiable.
   */
  private InternalAccess() {}

  /**
   * This supplier is used by the {@code {@link StructParserFactory
   * StructParserFactory}} to resolve implementations of the {@code StructParser}} class.
   *
   * @return {@code {@link Supplier}} that provides a {@code {@link StructParser }} implementation.
   */
  public static Supplier<StructParser> internalStructParserSupply() {
    return Suppliers.memoize(InternalStructParser::new);
  }
}
