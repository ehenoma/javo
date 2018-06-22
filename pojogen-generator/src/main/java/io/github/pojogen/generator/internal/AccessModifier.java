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

package io.github.pojogen.generator.internal;

import com.google.common.base.Preconditions;
import java.util.Optional;

/**
 * The {@code {@link AccessModifier }} enum represents the common {@code Java access-modifiers}.
 *
 * <p>It is used in the {@code Class-member models} contained in the {@code internal} package. And
 * provides a {@code keyword} for its {@code code representation}.
 *
 * @author Merlin
 * @see ClassModel
 * @see ConstructorModel
 * @see FieldModel
 * @see MethodModel
 * @since 1.0
 */
enum AccessModifier {

  /** Member or Class can only be accessed from within its package. */
  PACKAGE_PRIVATE,

  /** Member can only be accessed from within its class. */
  PRIVATE("private"),

  /** Member can only be accessed from within its package or implementation. */
  PROTECTED("protected"),

  /** Member can be accessed from everywhere. */
  PUBLIC("public");

  /** Optional keyword of the {@code {@link AccessModifier }} that is used when generating code. */
  private final Optional<String> keyword;

  /** Initializes the {@code {@link AccessModifier }} with an absent {@code keyword}. */
  AccessModifier() {
    this.keyword = Optional.empty();
  }

  /**
   * Initializes the {@code {@link AccessModifier }} from the {@code keyword}.
   *
   * @param keyword Keyword that is the {@code code} representation of the modifier.
   */
  AccessModifier(final String keyword) {
    Preconditions.checkNotNull(keyword);

    this.keyword = Optional.of(keyword);
  }

  /**
   * Optional keyword of the {@code {@link AccessModifier }} that is used when generating code.
   *
   * @return The modifiers {@code code} representation.
   */
  final Optional<String> getKeyword() {
    return this.keyword;
  }
}
