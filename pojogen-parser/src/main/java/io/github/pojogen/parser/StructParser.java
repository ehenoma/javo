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

import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;

import io.github.pojogen.struct.Struct;

/**
 * The {@code {@link StructParser}} is generating one or multiple {@code {@link Struct}} objects
 * from a source that contains valid {@code {@link Struct} prototypes}.
 *
 * <p>Following {@code {@link Struct}} objects are the blueprints for generating a {@code Pojo}. The
 * {@code {@link Struct} prototypes} might be written down to a file or simply passed as a {@code
 * {@link CharSequence}} to the {@code {@link StructParser}}. Internally implementations might use
 * technologies (like {@code RegEX}) which are known to be slow. This causes the parsing of large
 * input to block the {@code caller {@link Thread}} for quite some time. Therefore using the {@code
 * {@link StructParser}} in a non-blocking fashion ({@code Async}), will increase the responsiveness
 * of the software that is using it.
 *
 * <p>When the {@code {@link StructParser}} fails to parse certain sources a {@code {@link
 * StructParserException}} will most probably be thrown as response. In some cases unchecked
 * Exceptions like {@code {@link NullPointerException NPEs}} or {@code {@link
 * IllegalArgumentException} IAEs} are thrown as a response to invalid input.
 *
 * @author Merlin Osayimwen
 * @see Struct
 * @see StructParserException
 * @since 1.0
 */
public interface StructParser {

  /**
   * Parses {@code {@link Struct} structs} from the given {@code source}.
   *
   * <p>The amount of {@code {@link Struct} structs} parsed are from 0 to {@value
   * Integer#MAX_VALUE}.
   *
   * @param source The source that contains a {@code prototype} that is parsed.
   * @return Collection of parsed {@code {@link Struct}} instances.
   * @throws StructParserException Checked Exception that might be thrown during the process of
   *     parsing {@code Structures}.
   */
  Collection<Struct> parse(final InputStream source) throws StructParserException;

  /**
   * Parses {@code {@link Struct} structs} from the given {@code source}.
   *
   * <p>The amount of {@code {@link Struct} structs} parsed are from 0 to {@value
   * Integer#MAX_VALUE}.
   *
   * @param source The source that contains a {@code prototype} that is parsed.
   * @return Collection of parsed {@code {@link Struct}} instances.
   * @throws StructParserException Checked Exception that might be thrown during the process of
   *     parsing {@code Structures}.
   */
  Collection<Struct> parse(final Reader source) throws StructParserException;

  /**
   * Parses {@code {@link Struct} structs} from the given {@code source}.
   *
   * <p>The amount of {@code {@link Struct} structs} parsed are from 0 to {@value
   * Integer#MAX_VALUE}.
   *
   * @param source The source that contains a {@code prototype} that is parsed.
   * @return Collection of parsed {@code {@link Struct}} instances.
   * @throws StructParserException Checked Exception that might be thrown during the process of
   *     parsing {@code Structures}.
   */
  Collection<Struct> parse(final CharSequence source) throws StructParserException;

  /**
   * Parses one {@code {@link Struct}} from the {@code source}.
   *
   * @param source The source that contains a {@code prototype} that is parsed.
   * @return Collection of parsed {@code {@link Struct}} instances.
   * @throws StructParserException Checked Exception that might be thrown during the process of
   *     parsing {@code Structures}.
   */
  Struct parseOne(final InputStream source) throws StructParserException;

  /**
   * Parses one {@code {@link Struct}} from the {@code source}.
   *
   * @param source The source that contains a {@code prototype} that is parsed.
   * @return Collection of parsed {@code {@link Struct}} instances.
   * @throws StructParserException Checked Exception that might be thrown during the process of
   *     parsing {@code Structures}.
   */
  Struct parseOne(final Reader source) throws StructParserException;

  /**
   * Parses one {@code {@link Struct}} from the {@code source}.
   *
   * @param source The source that contains a {@code prototype} that is parsed.
   * @return Collection of parsed {@code {@link Struct}} instances.
   * @throws StructParserException Checked Exception that might be thrown during the process of
   *     parsing {@code Structures}.
   */
  Struct parseOne(final CharSequence source) throws StructParserException;
}
