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

final class InternalStructParser implements StructParser {

  /**
   * Modifier indicating that a struct or attribute is unmodifiable.
   */
  private static final String STRUCT_MODIFIER_CONST = "const";

  /**
   * Expression that parses struct definitions.
   */
  private static final Pattern STRUCT_REGEX = Pattern.compile(
      "(\\w+[ \\t])?(\\w+)[ \\t]+(\\w+)[ \\t]*\\{([\\w\\s:;]*)}");

  /**
   * Expression that parses attributes in the body of a struct definition.
   */
  private static final Pattern STRUCT_ATTRIBUTE_REGEX = Pattern.compile(
      "[ \\t]*(\\w+[ \\t]+)?(\\w+)[ \\t]*:[ \\t]*(\\w+).*");

  /**
   * Package private constructor of the {@code InternalStructParser}.
   */
  InternalStructParser() {
  }

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

  private Struct firstOrNone(final Collection<Struct> entry) {
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

  private Struct parseStructWithModifierFromMatcher(final Matcher source)
      throws StructParserException {
    final String structModifier = source.group(1);
    final String structType = source.group(2);
    final String structName = source.group(3);
    final String structBody = source.group(4);

    return new Struct(
        structName,
        this.parseAttributesFromBody(structBody),
        STRUCT_MODIFIER_CONST.equalsIgnoreCase(structModifier));
  }

  private Struct parseStructFromMatcher(final Matcher source) throws StructParserException {
    final String structType = source.group(1);
    final String structName = source.group(2);
    final String structBody = source.group(3);

    return new Struct(structName, this.parseAttributesFromBody(structBody));
  }

  private Collection<StructAttribute> parseAttributesFromBody(final String structBody)
      throws StructParserException {
    final Collection<StructAttribute> attributes = Lists.newLinkedList();
    final Matcher parsedBody = STRUCT_ATTRIBUTE_REGEX.matcher(structBody);
    while (parsedBody.find()) {
      switch (parsedBody.groupCount()) {
        case 3:
          attributes.add(this.parseAttributeWithModifierFromMatcher(parsedBody));
          break;
        case 2:
          attributes.add(this.parseAttributeFromMatcher(parsedBody));
          break;
        default:
          throw StructParserException.createWithMessage("The {source} contains invalid attributes");
      }
    }

    return attributes;
  }

  private StructAttribute parseAttributeFromMatcher(final Matcher parsedAttribute) {
    return new StructAttribute(parsedAttribute.group(1), parsedAttribute.group(2));
  }

  private StructAttribute parseAttributeWithModifierFromMatcher(final Matcher parsedAttribute) {
    final String modifier = parsedAttribute.group(1);
    // Currently using additional modification of the parsed group because the expression.
    // TODO: Fix the expression to make the code more scalable and configurable.
    final boolean constant = (modifier != null)
        && (STRUCT_MODIFIER_CONST.equalsIgnoreCase(modifier.replace("[ \t]", "")));
    return new StructAttribute(parsedAttribute.group(2), parsedAttribute.group(3), constant);
  }

}
