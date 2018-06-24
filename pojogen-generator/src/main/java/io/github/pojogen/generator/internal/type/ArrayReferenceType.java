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

import static java.text.MessageFormat.format;

import com.google.common.base.Preconditions;

public final class ArrayReferenceType extends ReferenceType {

  private ArrayReferenceType(final String rawType) {
    // While an array with primitive values might be seen as a primitive and
    // can't be treated as an {Object[]}, it is still no primitive in this case.
    super(rawType + "[]", false, true, false);
  }

  @Override
  public String toStringStatement(String variableName) {
    return format("Arrays.deepToString({0})", variableName);
  }

  @Override
  public String hashCodeStatement(String variableName) {
    return format("Arrays.deepHashCode({0})", variableName);
  }

  @Override
  public String equalsStatement(String variableName, String otherVariable) {
    return format("Arrays.deepEquals({0},{1})", variableName, otherVariable);
  }

  @Override
  public String copyStatement(String variableName) {
    return variableName + ".clone()";
  }

  public static ArrayReferenceType fromRawType(final String rawType) {
    Preconditions.checkNotNull(rawType);

    return new ArrayReferenceType(rawType);
  }
}
