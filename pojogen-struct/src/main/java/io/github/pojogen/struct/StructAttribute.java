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

package io.github.pojogen.struct;

import com.google.common.base.Preconditions;
import java.util.Objects;

public class StructAttribute {

  private final String name;
  private final String typeName;
  private final boolean constant;

  private StructAttribute(final StructAttribute attribute) {
    this(attribute.name, attribute.typeName, attribute.constant);
  }

  private StructAttribute(final String name) {
    this(name, Object.class.getSimpleName());
  }

  private StructAttribute(final String name, final String typeName) {
    this(name, typeName, false);
  }

  public StructAttribute(final String name, final String typeName, final boolean constant) {
    this.name = name;
    this.typeName = typeName;
    this.constant = constant;
  }

  public String getName() {
    return this.name;
  }

  public String getTypeName() {
    return this.typeName;
  }

  public boolean isConstant() {
    return this.constant;
  }

  @Override
  public String toString() {
    final StringBuilder contentBuilder = new StringBuilder();
    if (this.constant) {
      contentBuilder.append("const ");
    }

    return contentBuilder.append(this.name).append(": ").append(typeName).toString();
  }

  @Override
  public boolean equals(final Object object) {
    if (object == null) {
      return false;
    }

    if (!(object instanceof StructAttribute)) {
      return false;
    }

    final StructAttribute attribute = (StructAttribute) object;
    return (this.name.equals(attribute.name))
        && (this.typeName.equals(attribute.typeName))
        && (this.constant != attribute.constant);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.typeName, this.constant);
  }

  public static StructAttribute create(final String name) {
    Preconditions.checkNotNull(name);

    return new StructAttribute(name);
  }

  public static StructAttribute create(final String name, final String typeName) {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(typeName);

    return new StructAttribute(name, typeName);
  }

  public static StructAttribute create(
      final String name, final String typeName, final boolean constant) {

    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(typeName);

    return new StructAttribute(name, typeName, constant);
  }

  public static StructAttribute copyOf(final StructAttribute parent) {
    Preconditions.checkNotNull(parent);

    return new StructAttribute(parent);
  }
}
