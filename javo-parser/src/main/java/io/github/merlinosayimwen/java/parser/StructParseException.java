// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.java.parser;

/**
 * Checked Exception that is thrown in operations of the StructParser.
 * <p>
 * This Exception indicates, that the input is invalid and probably against
 * the specifications. It is also thrown when another checked exception occurred
 * during the process or parsing an prototype.
 *
 * @see Exception
 * @see StructParser
 * @since 1.0
 */
public final class StructParseException extends Exception {

  /**
   * Constructs the StructParseException with a {@code cause}.
   *
   * @param cause Failure that is causing the creation / throwing of this exception.
   */
  private StructParseException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs the StructParseException with a message.
   *
   * @param message Message that is giving information about the failure.
   */
  private StructParseException(String message) {
    super(message);
  }

  /**
   * Factory method that creates a StructParseException without arguments.
   *
   * @return New instance of the StructParseException.
   */
  public static StructParseException create() {
    return new StructParseException("Exception occurred while parsing the source");
  }

  /**
   * Factory method that creates a StructParseException with a cause.
   *
   * @param cause Failure that is causing the creation / throwing of this exception.
   * @return New instance of the StructParseException.
   */
  public static StructParseException createWithCause(final Throwable cause) {
    return new StructParseException(cause);
  }

  /**
   * Factory method that creates a {@code {@link StructParseException }} with a {@code message}.
   *
   * @param message Message that is giving information about the failure.
   * @return New instance of the {@code {@link StructParseException }}.
   */
  public static StructParseException createWithMessage(final String message) {
    return new StructParseException(message);
  }
}
