// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.java.parser;

import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;

import io.github.merlinosayimwen.javo.Struct;

/**
 * The StructParser is creating one or multiple Struct objects
 * from a source that contains valid struct definitions.
 * <p>
 * Following Struct objects are the blueprints for generating a Value Object.
 * The prototypes might be written down to a file or simply passed as a CharSequence
 * to the StructParser Internally implementations might use technologies {@code RegEX} which are
 * known to be slow. This causes the parsing of large input to block the caller Thread for quite
 * some time. Therefore using the StructParser in a non-blocking fashion ({@code Async}), will
 * increase the throughput and responsiveness of the software that is using it.
 * <p>
 * When the StructParser fails to parse certain sources a StructParseException will most probably be
 * thrown as response. In some cases unchecked Exceptions like NullPointerExceptions or IllegalArgumentExceptions
 * are thrown as a response to invalid input.
 *
 * @see Struct
 * @see StructParseException
 * @since 1.0
 */
public interface StructParser {

  /**
   * Parses Structs from the given source.
   *
   * @param source Source file that is read and parsed.
   * @return Set of parsed structs.
   * @throws StructParseException Thrown if the source is invalid.
   */
  Collection<Struct> parse(Path source) throws StructParseException;

  /**
   * Parses structs from the given source.
   *
   * @param source In memory source that is parsed.
   * @return Set of parsed structs.
   * @throws StructParseException Thrown if the source is invalid.
   */
  Collection<Struct> parse(CharSequence source) throws StructParseException;


  /**
   * Parses one {@code {@link Struct}} from the {@code source}.
   *
   * @param source The source that contains a {@code prototype} that is parsed.
   * @return Collection of parsed {@code {@link Struct}} instances.
   * @throws StructParseException Checked Exception that might be thrown during the process of
   *     parsing {@code Structures}.
   */
  Optional<Struct> parseSingle(Path path) throws StructParseException;

  /**
   * Parses one {@code {@link Struct}} from the {@code source}.
   *
   * @param source The source that contains a {@code prototype} that is parsed.
   * @return Collection of parsed {@code {@link Struct}} instances.
   * @throws StructParseException Checked Exception that might be thrown during the process of
   *     parsing {@code Structures}.
   */
  Optional<Struct> parseSingle(CharSequence source) throws StructParseException;
}