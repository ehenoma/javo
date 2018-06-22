/*
 * Copyright 2018 Merlin Osayimwen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.pojogen.parser.internal;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import io.github.pojogen.parser.StructParser;

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
   * This supplier is used by the {@code {@link io.github.pojogen.parser.StructParserFactory
   * StructParserFactory}} to resolve implementations of the {@code StructParser}} class.
   *
   * @return {@code {@link Supplier}} that provides a {@code {@link StructParser}} implementation.
   */
  public static Supplier<StructParser> internalStructParserSupply() {
    return Suppliers.memoize(InternalStructParser::new);
  }
}
