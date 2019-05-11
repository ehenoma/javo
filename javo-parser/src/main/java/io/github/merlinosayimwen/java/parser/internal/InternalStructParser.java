// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.java.parser.internal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import com.google.common.collect.Sets;
import io.github.merlinosayimwen.java.parser.StructParseException;
import io.github.merlinosayimwen.java.parser.StructParser;
import io.github.merlinosayimwen.javo.Struct;
import io.github.merlinosayimwen.javo.StructAttribute;

/**
 * Internal implementation of the StructParser interface that parses struct's
 * using the struct definition syntax.
 * <p>
 * The Parser Library is abstracting away internal implementation.
 * The InternalStructParser class is package-private and can only be accessed
 * through the InternalAccess class.
 *
 * @see StructParser
 * @see StructParseException
 * @see InternalAccess
 * @since 1.0
 */
final class InternalStructParser implements StructParser {

  private static final Pattern WHITESPACE_PATTERN = Pattern.compile(
    "[ \t]"
  );

  /** Expression that parses struct definitions. */
  private static final Pattern STRUCT_PATTERN = Pattern.compile(
    "(?:([\\w \\t]*))struct[ \\t]+(\\w+)[ \\t]*\\{([\\w\\s:;]*)}"
  );

  private static final int STRUCT_PATTERN_MODIFIER_GROUP = 1;

  private static final int STRUCT_PATTERN_NAME_GROUP = 2;

  private static final int STRUCT_PATTERN_BODY_GROUP = 3;

  private static final int STRUCT_PATTERN_GROUP_COUNT = 3;

  /** Expression that parses attributes in the body of a struct definition. */
  private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile(
    "[ \\t]*(?:(\\w+[ \\t]+)?)(\\w+)[ \\t]*:[ \\t]*([\\w\\[\\]<>]+).*"
  );

  private static final int ATTRIBUTE_PATTERN_MODIFIER_GROUP = 1;

  private static final int ATTRIBUTE_PATTERN_NAME_GROUP = 2;

  private static final int ATTRIBUTE_PATTERN_TYPE_GROUP = 3;

  private static final int ATTRIBUTE_PATTERN_GROUP_COUNT = 3;

  /** Package private constructor of the InternalStructParser */
  InternalStructParser() {}

  @Override
  public Optional<Struct> parseSingle(Path path) throws StructParseException {
    return this.firstOrNone(this.parse(path));
  }

  @Override
  public Optional<Struct> parseSingle(CharSequence source) throws StructParseException {
    return this.firstOrNone(this.parse(source));
  }

  /**
   * Returns the first element of in a set of structs.
   * The returned value is empty if the entry is {@link Collection#isEmpty() empty}.
   *
   * @param elements Set of structs that is checked for one element.
   * @return First element of the elements if it is not empty.
   */
  private Optional<Struct> firstOrNone(Collection<Struct> elements) {
    Preconditions.checkNotNull(elements);

    return elements.stream().findFirst();
  }

  @Override
  public Collection<Struct> parse(Path source) throws StructParseException {
    Preconditions.checkNotNull(source);
    Preconditions.checkArgument(Files.isRegularFile(source), "No file at path");

    try {
      String content = Files.readAllLines(source).stream().reduce("", String::concat);
      return parse(content);
    } catch (IOException ioFailure) {
      throw StructParseException.createWithCause(ioFailure);
    }
  }

  @Override
  public Collection<Struct> parse(CharSequence source) throws StructParseException {
    Preconditions.checkNotNull(source);
    Preconditions.checkArgument(source.length() > 0, "source is empty");

    Matcher scannedSource = STRUCT_PATTERN.matcher(source);
    Collection<Struct> structs = Lists.newLinkedList();
    while (scannedSource.find()) {
      structs.add(parseScannedGroups(scannedSource));
    }
    return structs;
  }

  private Struct parseScannedGroups(Matcher scanned) throws StructParseException {
    int groups = scanned.groupCount();
    if (groups != STRUCT_PATTERN_GROUP_COUNT) {
      throw StructParseException.createWithMessage("the source is invalid");
    }
    return parseStruct(scanned);
  }

  /**
   * Parses a struct with modifiers from a set of tokens that have been
   * scanned using the regex pattern.
   *
   * @param tokens Set of scanned tokens.
   * @return Nonnull parsed struct.
   * @throws StructParseException Thrown if the syntax is invalid.
   */
  private Struct parseStruct(Matcher tokens) throws StructParseException {
    String body = tokens.group(STRUCT_PATTERN_BODY_GROUP);
    String modifiers = tokens.group(STRUCT_PATTERN_MODIFIER_GROUP);

    return Struct.newBuilder()
      .withName(tokens.group(STRUCT_PATTERN_NAME_GROUP))
      .withAttributes(parseAttributesFromBody(body))
      .withModifiers(readOptionalModifiers(modifiers))
      .create();
  }

  private Collection<Struct.Modifier> readOptionalModifiers(String modifiers)
    throws StructParseException {

    String[] split = modifiers.split(WHITESPACE_PATTERN.pattern());
    if (split.length == 0) {
      return Collections.emptyList();
    }
    Collection<Struct.Modifier> read = Sets.newHashSet();
    for (String modifier : split) {
      read.add(Struct.Modifier.fromCode(modifier).orElseThrow(
        () -> StructParseException.createWithMessage("no such modifier " + modifier)
      ));
    }
    return read;
  }

  /**
   * Parses a Set of attributes from the body.
   *
   * @param body Body of the struct which contains zero or more attributes.
   * @return Set of zero ore more attributes.
   * @throws StructParseException Thrown is the syntax is invalid.
   */
  private Collection<StructAttribute> parseAttributesFromBody(String body)
      throws StructParseException {

    Matcher scanned = ATTRIBUTE_PATTERN.matcher(body);
    Collection<StructAttribute> attributes = Lists.newLinkedList();
    while (scanned.find()) {
      attributes.add(parseAttribute(scanned));
    }
    return attributes;
  }

  private StructAttribute parseAttribute(Matcher tokens) throws StructParseException{
    return StructAttribute.create("");
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).toString();
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