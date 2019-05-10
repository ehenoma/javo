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

import com.google.common.base.MoreObjects;
import io.github.merlinosayimwen.pojogen.struct.util.ObjectChecks;
import java.util.Objects;

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
