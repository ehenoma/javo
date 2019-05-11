// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal.type;

import com.google.common.base.Preconditions;

import static java.text.MessageFormat.format;

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
