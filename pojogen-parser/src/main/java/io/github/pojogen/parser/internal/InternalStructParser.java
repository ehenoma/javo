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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import io.github.pojogen.parser.StructParser;
import io.github.pojogen.parser.StructParserException;
import io.github.pojogen.parser.util.MoreSuppliers;
import io.github.pojogen.struct.Struct;
import io.github.pojogen.struct.StructAttribute;

/**
 * Internal implementation of the {@code {@link StructParser}} interface that follows the default
 * specification of {@code {@link Struct} prototypes}.
 *
 * <p>The {@code Parser Library} is using abstraction to hide internal implementation and
 * encapsulate code or data. The {@code {@link InternalStructParser}} class is package-private and
 * can only be accessed through the {@code {@link InternalAccess}} class.
 *
 * @see StructParser
 * @see StructParserException
 * @see InternalAccess
 * @since 1.0
 */
final class InternalStructParser implements StructParser {

  /** Modifier indicating that a struct or attribute is unmodifiable. */
  private static final String STRUCT_MODIFIER_CONST = "const";

  /** Expression that parses struct definitions. */
  private static final Pattern STRUCT_REGEX =
      Pattern.compile("(\\w+[ \\t])?(\\w+)[ \\t]+(\\w+)[ \\t]*\\{([\\w\\s:;]*)}");

  /** Expression that parses attributes in the body of a struct definition. */
  private static final Pattern STRUCT_ATTRIBUTE_REGEX =
      Pattern.compile("[ \\t]*(\\w+[ \\t]+)?(\\w+)[ \\t]*:[ \\t]*([\\w\\[\\]<>]+).*");

  /** Package private constructor of the {@code InternalStructParser}. */
  InternalStructParser() {}

  @Override
  public Struct parseOne(final InputStream source) throws StructParserException {
    return this.firstOrNone(this.parse(source));
  }

  @Override
  public Struct parseOne(final Reader source) throws StructParserException {
    return this.firstOrNone(this.parse(source));
  }

  @Override
  public Struct parseOne(final CharSequence source) throws StructParserException {
    return this.firstOrNone(this.parse(source));
  }

  /**
   * Returns the first element of a {@code {@link Collection}} of {@code {@link Struct Structs}}.
   * The {@code return-value} is {@code null} if the {@code entry} is {@link Collection#isEmpty()
   * empty}. An unchecked exception is thrown whenever the {@code entry} is {@code null}.
   *
   * @param entry Collection of {@code {@link Struct Structs}} that is checked for one element.
   * @return First element of the {@code entry} if it is not {@link Collection#isEmpty() empty}.
   */
  private Struct firstOrNone(final Collection<Struct> entry) {
    Preconditions.checkNotNull(entry);

    return entry.stream().findFirst().orElseGet(MoreSuppliers.supplyNull());
  }

  @Override
  public Collection<Struct> parse(InputStream source) throws StructParserException {
    Preconditions.checkNotNull(source);

    try (final Reader sourceReader = new InputStreamReader(source)) {
      return this.parse(sourceReader);
    } catch (final IOException streamCreationFailure) {
      throw StructParserException.createWithCause(streamCreationFailure);
    }
  }

  @Override
  public Collection<Struct> parse(final Reader source) throws StructParserException {
    Preconditions.checkNotNull(source);

    try (final Scanner sourceReader = new Scanner(source)) {
      Preconditions.checkArgument(sourceReader.hasNextLine(), "The {source} is empty");

      final StringBuilder combinedSource = new StringBuilder();
      for (String line = sourceReader.nextLine(); line != null; line = sourceReader.nextLine()) {
        combinedSource.append(line);
      }

      return this.parse(combinedSource.toString());
    }
  }

  @Override
  public Collection<Struct> parse(final CharSequence source) throws StructParserException {
    Preconditions.checkNotNull(source);
    Preconditions.checkArgument(source.length() > 0);

    final Collection<Struct> structs = new LinkedList<>();
    final Matcher parsedSource = STRUCT_REGEX.matcher(source);
    while (parsedSource.find()) {
      switch (parsedSource.groupCount()) {
        case 4:
          structs.add(this.parseStructWithModifierFromMatcher(parsedSource));
          break;
        case 3:
          structs.add(this.parseStructFromMatcher(parsedSource));
          break;
        default:
          throw StructParserException.createWithCause(
              new IllegalArgumentException("The {source} is not valid."));
      }
    }

    return structs;
  }

  /**
   * Parses a {@code {@link Struct}} with modifier from a {@code {@link Matcher source}} that has
   * been generated by using a special {@code {@link Pattern RegEX Pattern}}. The struct will be
   * filled with {@code {@link StructAttribute attributes}} which are parsed from its {@code
   * content}.
   *
   * @param source {@code {@link Matcher Source}} that the {@code {@link Struct}} is parsed from.
   * @return Nonnull parsed {@code {@link Struct}} instance.
   * @throws StructParserException Exception that occurred while parsing the {@code {@link Struct}}
   *     from the {@code source}.
   */
  private Struct parseStructWithModifierFromMatcher(final Matcher source)
      throws StructParserException {

    final String structModifier = source.group(1);
    final String structType = source.group(2);
    final String structName = source.group(3);
    final String structBody = source.group(4);

    return Struct.create(
        structName,
        this.parseAttributesFromBody(structBody),
        STRUCT_MODIFIER_CONST.equalsIgnoreCase(structModifier));
  }

  /**
   * Parses a {@code {@link Struct}} without modifier from a {@code {@link Matcher source}} that has
   * been generated by using a special {@code {@link Pattern RegEX Pattern}}. The struct will be
   * filled with {@code {@link StructAttribute attributes}} which are parsed from its {@code
   * content}.
   *
   * @param source {@code {@link Matcher Source}} that the {@code {@link Struct}} is parsed from.
   * @return Nonnull parsed {@code {@link Struct}} instance.
   * @throws StructParserException Exception that occurred while parsing the {@code {@link Struct}}
   *     from the {@code source}.
   */
  private Struct parseStructFromMatcher(final Matcher source) throws StructParserException {
    final String structType = source.group(1);
    final String structName = source.group(2);
    final String structBody = source.group(3);

    return Struct.create(structName, this.parseAttributesFromBody(structBody));
  }

  /**
   * Parses a Collection of {@code {@linkplain StructAttribute StructAttributes}} from the {@code
   * structBody}.
   *
   * @param structBody Body of the struct which contains zero or more attributes.
   * @return Collection with one or more attributes that were contained in the {@code structBody}.
   * @throws StructParserException Checked Exception that might be thrown when the amount of a
   *     parsed attributes groups is invalid.
   */
  private Collection<StructAttribute> parseAttributesFromBody(final String structBody)
      throws StructParserException {

    final Collection<StructAttribute> attributes = Lists.newLinkedList();
    final Matcher parsedBody = STRUCT_ATTRIBUTE_REGEX.matcher(structBody);
    while (parsedBody.find()) {
      switch (parsedBody.groupCount()) {
        case 3:
          // Parses and appends the attribute which has a modifier.
          attributes.add(this.parseAttributeWithModifierFromMatcher(parsedBody));
          break;
        case 2:
          // Parses and appends the attribute which has no modifier.
          attributes.add(this.parseAttributeFromMatcher(parsedBody));
          break;
        default:
          // Amount of groups is against the specifications.
          throw StructParserException.createWithMessage("The {source} contains invalid attributes");
      }
    }

    return attributes;
  }

  /**
   * Parses a {@code {@link StructAttribute}} object from the {@code parsedAttribute {@link
   * Matcher}} by resolving its groups.
   *
   * @param parsedAttribute Matcher that groups the attributes data.
   * @return Instance of a {@code {@link StructAttribute}} parsed from the {@code parsedAttribute}.
   */
  private StructAttribute parseAttributeFromMatcher(final Matcher parsedAttribute) {
    return StructAttribute.create(parsedAttribute.group(1), parsedAttribute.group(2));
  }

  /**
   * Parses a {@code {@link StructAttribute}} object with modifier from the {@code parsedAttribute
   * {@link Matcher}} by resolving and evaluating its groups.
   *
   * @param parsedAttribute Matcher that groups the attributes data.
   * @return Instance of a {@code {@link StructAttribute}} parsed from the {@code parsedAttribute}.
   */
  private StructAttribute parseAttributeWithModifierFromMatcher(final Matcher parsedAttribute) {
    final String modifier = parsedAttribute.group(1);
    // Currently using additional modification of the parsed group because the expression.
    // TODO: Fix the expression to make the code more scalable and configurable.
    final boolean constant =
        (modifier != null)
            && (STRUCT_MODIFIER_CONST.equalsIgnoreCase(modifier.replace("[ \t]", "")));
    return new StructAttribute(parsedAttribute.group(2), parsedAttribute.group(3), constant);
  }

  @Override
  public String toString() {
    return String.format(
        "%s{identity=%d}", StructParser.class.getSimpleName(), System.identityHashCode(this));
  }

  @Override
  public int hashCode() {
    return System.identityHashCode(this);
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }

    // Since the InternalStructParser is stateless instances have no significance
    // and are effectively equal to each other.
    return other instanceof InternalStructParser;
  }
}
