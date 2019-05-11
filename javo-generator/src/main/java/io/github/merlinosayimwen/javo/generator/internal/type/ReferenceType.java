// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal.type;

import java.util.Objects;

import com.google.common.base.MoreObjects;

public abstract class ReferenceType {

  private final String typeName;
  private final boolean primitive;
  private final boolean parameterized;
  private final boolean concrete;

  public ReferenceType(
      final String typeName,
      final boolean generic,
      final boolean concrete,
      final boolean primitive) {

    this.typeName = typeName;
    this.parameterized = generic;
    this.concrete = concrete;
    this.primitive = primitive;
  }

  public abstract String toStringStatement(final String variableName);

  public abstract String hashCodeStatement(final String variableName);

  public abstract String copyStatement(final String variableName);

  public abstract String equalsStatement(final String variableName, final String otherVariable);

  public boolean isParameterized() {
    return parameterized;
  }

  public boolean isConcrete() {
    return concrete;
  }

  public boolean isPrimitive() {
    return primitive;
  }

  public String getTypeName() {
    return typeName;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("typeName", this.typeName)
        .add("parameterized", this.parameterized)
        .add("concrete", this.concrete)
        .add("primitive", this.primitive)
        .toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.typeName, this.parameterized, this.concrete, this.primitive);
  }

  @Override
  public boolean equals(final Object checkTarget) {
    return ObjectChecks.equalsDefinitely(this, checkTarget)
        .orElseGet(() -> deepEquals(checkTarget));
  }

  private boolean deepEquals(final Object checkTarget) {
    if (!(checkTarget instanceof ReferenceType)) {
      return false;
    }

    final ReferenceType otherType = (ReferenceType) checkTarget;
    return this.typeName.equals(otherType.typeName)
        && this.parameterized == otherType.parameterized
        && this.concrete == otherType.concrete
        && this.primitive == otherType.primitive;
  }
}
