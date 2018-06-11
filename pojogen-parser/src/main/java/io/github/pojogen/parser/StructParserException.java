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

/**
 * Checked {@code {@link Exception}} that is thrown in operations of the {@code {@link
 * StructParser}}.
 *
 * <p>This {@code {@link Exception}} indicates, that the input is invalid and probably against the
 * specifications. It is also thrown when another checked exception occurred during the process or
 * {@code parsing} an {@code prototype}.
 *
 * @author Merlin Osayimwen
 * @see Exception
 * @see StructParser
 * @since 1.0
 */
public final class StructParserException extends Exception {

  /**
   * Constructs the {@code {@link StructParserException}} with a {@code cause}.
   *
   * @param cause Failure that is causing the creation / throwing of this exception.
   */
  private StructParserException(final Throwable cause) {
    super(cause);
  }

  /**
   * Constructs the {@code {@link StructParserException}} with a {@code message}.
   *
   * @param message Message that is giving information about the failure.
   */
  private StructParserException(final String message) {
    super(message);
  }

  /**
   * Factory method that creates a {@code {@link StructParserException}} without arguments.
   *
   * @return New instance of the {@code {@link StructParserException}}.
   */
  public static StructParserException create() {
    return new StructParserException("Exception occurred while parsing the source");
  }

  /**
   * Factory method that creates a {@code {@link StructParserException}} with a {@code cause}.
   *
   * @param cause Failure that is causing the creation / throwing of this exception.
   * @return New instance of the {@code {@link StructParserException}}.
   */
  public static StructParserException createWithCause(final Throwable cause) {
    return new StructParserException(cause);
  }

  /**
   * Factory method that creates a {@code {@link StructParserException}} with a {@code message}.
   *
   * @param message Message that is giving information about the failure.
   * @return New instance of the {@code {@link StructParserException}}.
   */
  public static StructParserException createWithMessage(final String message) {
    return new StructParserException(message);
  }
}
