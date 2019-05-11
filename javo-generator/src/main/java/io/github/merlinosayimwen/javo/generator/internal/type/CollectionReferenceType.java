// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal.type;

import com.google.common.base.Preconditions;

import static java.text.MessageFormat.format;

public final class CollectionReferenceType extends ReferenceType {

  private CollectionReferenceType(final String typeName, final boolean concrete) {
    super(typeName, true, concrete, false);
  }

  @Override
  public String copyStatement(String variableName) {
    return null;
  }

  @Override
  public String equalsStatement(String variableName, String otherVariable) {
    return format("Arrays.deepEquals({0}.toArray(),{1}.toArray())", variableName, otherVariable);
  }

  @Override
  public String hashCodeStatement(String variableName) {
    return format("Arrays.deepHashCode({0}.toArray())", variableName);
  }

  @Override
  public String toStringStatement(String variableName) {
    return format("Joiner.on(\", \").join({0})", variableName);
  }

  public static CollectionReferenceType createConcrete(final String typeName) {
    return create(typeName, false);
  }

  public static CollectionReferenceType createAbstract(final String typeName) {
    return create(typeName, false);
  }

  public static CollectionReferenceType create(final String typeName, final boolean concrete) {
    Preconditions.checkNotNull(typeName);

    return new CollectionReferenceType(typeName, concrete);
  }
}
