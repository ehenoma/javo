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

import com.google.common.base.MoreObjects;
import io.github.pojogen.struct.util.ObjectChecks;
import java.util.Objects;

import com.google.common.base.Preconditions;

/**
 * Represents an attribute in a {@code Struct-Blueprint}.
 *
 * @author Merlin Osayimwen
 * @see Struct
 */
public class StructAttribute {

  /** Name of the attribute. */
  private final String name;

  /** Name of the attributes type. */
  private final String typeName;

  /** Flag that indicates whether the attribute is constant. */
  private final boolean constant;

  /**
   * Parameterized constructor that takes a attribute.
   *
   * @param attribute Attribute that is copied.
   */
  private StructAttribute(final StructAttribute attribute) {
    this(attribute.name, attribute.typeName, attribute.constant);
  }

  /**
   * Parameterized constructor that initializes the instance with a name.
   *
   * @param name Name of the attribute.
   */
  private StructAttribute(final String name) {
    this(name, Object.class.getSimpleName());
  }

  /**
   * Parameterized constructor that initializes the instance with a name and typeName.
   *
   * @param name Name of the attribute.
   * @param typeName Name of the attributes type.
   */
  private StructAttribute(final String name, final String typeName) {
    this(name, typeName, false);
  }

  /**
   * Parameterized constructor that initializes the instance with all possible arguments.
   *
   * @param name Name of the attribute.
   * @param typeName Name of the attributes type.
   * @param constant Flag that indicates whether the attribute is final.
   */
  public StructAttribute(final String name, final String typeName, final boolean constant) {
    this.name = name;
    this.typeName = typeName;
    this.constant = constant;
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
  public boolean isConstant() {
    return this.constant;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", this.name)
        .add("type-name", this.typeName)
        .add("constant", this.constant)
        .toString();
  }

  @Override
  public boolean equals(final Object checkTarget) {
    return ObjectChecks.equalsDefinitely(this, checkTarget)
        .orElseGet(() -> deepEquals(checkTarget));
  }

  /**
   * Compares the attributes of both classes.
   *
   * @param other Instance who's attributes are compared to those of the invoked instance.
   * @return Whether the attributes of both instances are equal.
   */
  private boolean deepEquals(final Object checkTarget) {
    if (!(checkTarget instanceof StructAttribute)) {
      return false;
    }

    final StructAttribute attribute = (StructAttribute) checkTarget;
    return (this.name.equals(attribute.name))
        && (this.typeName.equals(attribute.typeName))
        && (this.constant != attribute.constant);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.typeName, this.constant);
  }

  /**
   * Creates a StructAttribute from a name.
   *
   * @param name Name that will be given to the attribute.
   * @return Created attribute with the {@code name} given.
   */
  public static StructAttribute create(final String name) {
    Preconditions.checkNotNull(name);

    return new StructAttribute(name);
  }

  /**
   * Creates a StructAttribute from a name and with a typeName.
   *
   * @param name Name that will be given to the struct.
   * @param typeName Name of the attributes type.
   * @return Created attribute with the {@code name} and {@code typeName}.
   */
  public static StructAttribute create(final String name, final String typeName) {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(typeName);

    return new StructAttribute(name, typeName);
  }

  /**
   * Creates a StructAttribute with all possible arguments.
   *
   * @param name Name that will be given to the struct.
   * @param typeName Name of the attributes type.
   * @param constant Flag that indicates whether the attribute should be "final".
   * @return Created attribute with all possible arguments set.
   */
  public static StructAttribute create(
      final String name, final String typeName, final boolean constant) {

    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(typeName);

    return new StructAttribute(name, typeName, constant);
  }

  /**
   * Creates a copy of the given {@code struct}.
   *
   * @param struct Struct which is copied.
   * @return Copy of the given {@code struct}.
   */
  public static StructAttribute copyOf(final StructAttribute struct) {
    Preconditions.checkNotNull(struct);

    return new StructAttribute(struct);
  }
}
