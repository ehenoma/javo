// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal.type;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

import static java.util.regex.Pattern.compile;

public final class ReferenceTypeParser {

  private static final Pattern COLLECTION_PARSE_EXPRESSION =
      compile("(\\w+)?(?:<(\\w+)>)|(\\[(\\w+)])");

  private static final String FALLBACK_COLLECTION_TYPE_NAME = "Collection";

  private ReferenceTypeParser() {}

  private static boolean hasGroups(final Matcher matcher, final int... groups) {
    try {
      for (final int group : groups) {
        if (matcher.group(group) == null) {
          return false;
        }
      }
    } catch (final RuntimeException failure) {
      // Exception can be ignored since it is just an indicator for the
      // group being out of bounds ir illegal.
    }

    return true;
  }

  public static ReferenceTypeParser create() {
    return new ReferenceTypeParser();
  }

  public ReferenceType parseReference(final String entry) {
    Preconditions.checkNotNull(entry);

    final Matcher parsed = ReferenceTypeParser.COLLECTION_PARSE_EXPRESSION.matcher(entry);
    if (!parsed.matches()) {
      return ObjectReferenceType.create(entry, false);
    }

    return this.parseCollectionType(parsed)
        .orElseGet(() -> ObjectReferenceType.create(entry, false));
  }

  private Optional<ReferenceType> parseCollectionType(final Matcher matcher) {
    if (matcher.groupCount() != 4) {
      throw new IllegalArgumentException("Invalid Input");
    }

    if (ReferenceTypeParser.hasGroups(matcher, 2)) {
      return Optional.of(this.parseGenericCollectionType(matcher));
    }

    if (ReferenceTypeParser.hasGroups(matcher, 3, 4)) {
      return Optional.of(this.parseArrayType(matcher));
    }
    return Optional.empty();
  }

  private ReferenceType parseArrayType(final Matcher matcher) {
    return ArrayReferenceType.fromRawType(matcher.group(4));
  }

  private ReferenceType parseGenericCollectionType(final Matcher matcher) {
    final String collectionType;
    if (matcher.group(1) != null) {
      collectionType = matcher.group(1);
    } else {
      collectionType = ReferenceTypeParser.FALLBACK_COLLECTION_TYPE_NAME;
    }

    return CollectionReferenceType.create(collectionType + "<" + matcher.group(2) + ">", false);
  }
}
