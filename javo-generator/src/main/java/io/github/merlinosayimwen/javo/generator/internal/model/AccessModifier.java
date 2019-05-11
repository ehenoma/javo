// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal.model;

import java.util.Optional;

import com.google.common.base.Preconditions;

/**
 * The {@code {@link AccessModifier }} enum represents the common {@code Java access-modifiers}.
 *
 * <p>It is used in the {@code Class-member models} contained in the {@code internal} package. And
 * provides a {@code keyword} for its {@code code representation}.
 *
 * @since 1.0
 */
public enum AccessModifier {

  /** Member or Class can only be accessed from within its package. */
  PACKAGE_PRIVATE,

  /** Member can only be accessed from within its class. */
  PRIVATE("private"),

  /** Member can only be accessed from within its package or implementation. */
  PROTECTED("protected"),

  /** Member can be accessed from everywhere. */
  PUBLIC("public");

  /** Optional keyword of the {@code {@link AccessModifier }} that is used when generating code. */
  private final String keyword;

  /** Initializes the {@code {@link AccessModifier }} with an absent {@code keyword}. */
  AccessModifier() {
    this.keyword = null;
  }

  /**
   * Initializes the {@code {@link AccessModifier }} from the {@code keyword}.
   *
   * @param keyword Keyword that is the {@code code} representation of the modifier.
   */
  AccessModifier(final String keyword) {
    Preconditions.checkNotNull(keyword);

    this.keyword = keyword;
  }

  /**
   * Optional keyword of the {@code {@link AccessModifier }} that is used when generating code.
   *
   * @return The modifiers {@code code} representation.
   */
  final Optional<String> getKeyword() {
    return Optional.ofNullable(this.keyword);
  }
}
