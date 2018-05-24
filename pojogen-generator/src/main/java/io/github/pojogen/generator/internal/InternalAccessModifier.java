package io.github.pojogen.generator.internal;

import com.google.common.base.Preconditions;
import java.util.Optional;

/**
 * The {@code {@link InternalAccessModifier}} enum represents the common {@code Java
 * access-modifiers}.
 *
 * It is used in the {@code Class-member models} contained in the {@code internal} package. And
 * provides a {@code keyword} for its {@code code representation}.
 *
 * @author Merlin
 * @see InternalClassModel
 * @see InternalConstructorModel
 * @see InternalFieldModel
 * @see InternalMethodModel
 * @since 1.0
 */
enum InternalAccessModifier {

  /**
   * Member or Class can only be accessed from within its package.
   */
  PACKAGE_PRIVATE,

  /**
   * Member can only be accessed from within its class.
   */
  PRIVATE("private"),

  /**
   * Member can only be accessed from within its package or implementation.
   */
  PROTECTED("protected"),

  /**
   * Member can be accessed from everywhere.
   */
  PUBLIC("public");

  /**
   * Optional keyword of the {@code {@link InternalAccessModifier}} that is used when generating
   * code.
   */
  private final Optional<String> keyword;

  /**
   * Initializes the {@code {@link InternalAccessModifier}} with an absent {@code keyword}.
   */
  InternalAccessModifier() {
    this.keyword = Optional.empty();
  }

  /**
   * Initializes the {@code {@link InternalAccessModifier}} from the {@code keyword}.
   *
   * @param keyword Keyword that is the {@code code} representation of the modifier.
   */
  InternalAccessModifier(final String keyword) {
    Preconditions.checkNotNull(keyword);

    this.keyword = Optional.of(keyword);
  }

  /**
   * Optional keyword of the {@code {@link InternalAccessModifier}} that is used when generating
   * code.
   *
   * @return The modifiers {@code code} representation.
   */
  final Optional<String> getKeyword() {
    return this.keyword;
  }

}
