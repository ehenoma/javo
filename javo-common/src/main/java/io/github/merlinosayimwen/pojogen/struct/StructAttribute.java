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

package io.github.merlinosayimwen.pojogen.struct;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

/**
 * Represents an attribute in a {@code Struct-Blueprint}.
 *
 * @see Struct
 */
public class StructAttribute {

  enum Modifier {
    IMMUTABLE
  }

  /** Name of the attribute. */
  private String name;

  /** Name of the attributes type. */
  private String typeName;

  /** Flag that indicates whether the attribute is constant. */
  private Collection<Modifier> modifiers;

  /**
   * Parameterized constructor that initializes the instance with all possible arguments.
   *
   * @param name Name of the attribute.
   * @param typeName Name of the attributes type.
   * @param modifiers Set of modifiers that influence the generated java class.
   */
  private StructAttribute(String name, String typeName, Collection<Modifier> modifiers) {
    this.name = name;
    this.typeName = typeName;
    this.modifiers = modifiers;
  }

  /**
   * Gets the name of the attribute.
   *
   * @return Name of the attribute.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets the name of the attributes type.
   *
   * @return Name of the attributes type
   */
  public String getTypeName() {
    return this.typeName;
  }

  /**
   * Returns whether the attribute is a constant.
   *
   * @return Is the attribute a constant?
   */
  public boolean isImmutable() {
    return this.hasModifier(Modifier.IMMUTABLE);
  }

  public boolean hasModifier(Modifier modifier) {
    return modifiers.contains(modifier);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", this.name)
        .add("typeName", this.typeName)
        .add("modifiers", this.modifiers.size())
        .toString();
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (!(object instanceof StructAttribute)) {
      return false;
    }
    return deepEquals((StructAttribute) object);
  }

  private boolean deepEquals(StructAttribute attribute) {
    return this.name.equals(attribute.name)
        && this.typeName.equals(attribute.typeName)
        && Arrays.deepEquals(modifiers.toArray(), attribute.modifiers.toArray());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.typeName, Arrays.deepHashCode(modifiers.toArray()));
  }

  /**
   * Creates a StructAttribute from a name.
   *
   * @param name Name that will be given to the attribute.
   * @return Created attribute with no modifiers.
   */
  public static StructAttribute create(String name) {
    Preconditions.checkNotNull(name);

    return StructAttribute.create(name, Object.class.getSimpleName());
  }

  /**
   * Creates a StructAttribute from a name and with a typeName.
   *
   * @param name Name that will be given to the struct.
   * @param typeName Name of the attributes type.
   * @return Created attribute with no modifiers.
   */
  public static StructAttribute create(String name, String typeName) {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(typeName);

    return StructAttribute.create(name, typeName, Collections.emptyList());
  }

  /**
   * Creates a StructAttribute with all possible arguments.
   *
   * @param name Name that will be given to the struct.
   * @param typeName Name of the attributes type.
   * @param modifiers Set of modifiers that influence the generated java class.
   *
   * @return Created attribute with all possible arguments set.
   */
  public static StructAttribute create(String name, String typeName, Iterable<Modifier> modifiers) {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(typeName);

    return new StructAttribute(name, typeName, Sets.newEnumSet(modifiers, Modifier.class));
  }
}
