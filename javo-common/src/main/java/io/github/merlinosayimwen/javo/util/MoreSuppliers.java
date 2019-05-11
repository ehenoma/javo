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

package io.github.merlinosayimwen.javo.util;

import java.util.function.Supplier;

/**
 * Provides additional factory methods that help working with the {@code {@link Supplier}} class.
 * This class is more of an complement to {@code Guavas} utility-class.
 *
 * @see Supplier
 * @since 1.0
 */
public final class MoreSuppliers {

  /** Private constructor that prohibits construction of the utility-class. */
  private MoreSuppliers() {}

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
