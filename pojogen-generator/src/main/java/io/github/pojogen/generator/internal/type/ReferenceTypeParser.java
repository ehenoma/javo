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
import javax.annotation.Nullable;

public final class ReferenceTypeParser {

  private static final Pattern COLLECTION_PARSE_EXPRESSION =
      compile("(\\w+)?(?:<(\\w+)>)|(\\[(\\w+)])");

  private ReferenceTypeParser() {}

  public ReferenceType parseReference(final String entry) {
    Preconditions.checkNotNull(entry);

    final Matcher parsed = ReferenceTypeParser.COLLECTION_PARSE_EXPRESSION.matcher(entry);
    if (!parsed.matches()) {
      // TODO: Implement abstract/concrete checks.
      return PlainReferenceType.create(entry, false);
    }

    return this.parseCollectionType(parsed)
        .orElseGet(() -> PlainReferenceType.create(entry, false));
  }

  private Optional<ReferenceType> parseCollectionType(final Matcher matcher) {
    if (matcher.groupCount() != 4) {
      throw new IllegalArgumentException("Invalid Input");
    }
    try {
      if (matcher.group(1) != null && matcher.group(2) != null) {
        return Optional.of(this.parseGenericCollectionType(matcher));
      }
    } catch (final RuntimeException failure) {
      // Just continue... The exception does not need any handling
      // since it's just an indicator that the groups are not available.
    }

    try {
      if (matcher.group(3) != null && matcher.group(4) != null) {
        return Optional.of(this.parseArrayType(matcher));
      }
    } catch (final RuntimeException failure) {
      // This does not need handling neither.
    }
    return Optional.empty();
  }

  private ReferenceType parseArrayType(final Matcher matcher) {
    return ArrayReferenceType.fromRawType(matcher.group(4));
  }

  private ReferenceType parseGenericCollectionType(final Matcher matcher) {
    return null;
  }

  public static ReferenceTypeParser create() {
    return new ReferenceTypeParser();
  }
}
