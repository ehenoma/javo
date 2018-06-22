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

package io.github.pojogen.parser;

import java.util.function.Supplier;

import io.github.pojogen.parser.internal.InternalAccess;

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

  /**
   * Creates a new instance of the StructParserFactory.
   *
   * @return Instance of the factory.
   */
  public static StructParserFactory create() {
    return new StructParserFactory();
  }
}
