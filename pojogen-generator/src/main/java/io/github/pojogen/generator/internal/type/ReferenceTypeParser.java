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

package io.github.pojogen.generator.internal.type;

import static java.util.regex.Pattern.compile;

import com.google.common.base.Preconditions;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ReferenceTypeParser {

  private static final Pattern COLLECTION_PARSE_EXPRESSION =
      compile("(\\w+)?(?:<(\\w+)>)|(\\[(\\w+)])");

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

    if (ReferenceTypeParser.hasGroups(matcher, 1, 2)) {
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
    return CollectionReferenceType.create(matcher.group(1) + "<" + matcher.group(2) + ">", false);
  }
}
