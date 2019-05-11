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

package io.github.merlinosayimwen.javo.generator.internal.model;

import java.util.Objects;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import io.github.merlinosayimwen.javo.generator.internal.GenerationContext;
import io.github.merlinosayimwen.javo.generator.internal.GenerationStep;
import io.github.merlinosayimwen.javo.generator.internal.type.ReferenceType;
import io.github.merlinosayimwen.javo.generator.internal.type.ObjectReferenceType;

import static java.text.MessageFormat.format;

/**
 * Immutable {@code value object} that is representing a local or global {@code variable}.
 *
 * <p>The {@code {@link VariableModel }} class is used to describe method parameters, local and
 * global variables that can be {@code immutable (final)}. It is open for implementations in order
 * for more explicit objects of type {@code variable}, to build up on this model. An example for an
 * implementation is the {@code {@link FieldModel }} class which has a additional {@code access
 * modifier}. The constructors are accessible from inside the package but using the {@code
 * factory-methods} is highly encouraged; they will do additional safety checks.
 *
 * @see ClassModel
 * @see FieldModel
 * @since 1.0
 */
public class VariableModel implements GenerationStep {

  /** The default {type} that is used as a {fallback value}. */
  protected static final ReferenceType DEFAULT_TYPE = ObjectReferenceType.createConcrete("Object");

  /** The default {modifiable} flag that is used as a {fallback value}. */
  protected static final boolean DEFAULT_MODIFIABLE = true;

  /** Name of the variable. */
  protected final String name;

  /** Name of the variables type. */
  protected final ReferenceType type;

  /** Flag that indicates whether the variable is not {@code final}. */
  protected final boolean modifiable;

  /**
   * Parameterized constructor that initializes the instance from a {@code name}.
   *
   * @param name Name of the variable.
   */
  protected VariableModel(final String name) {
    this(name, VariableModel.DEFAULT_TYPE);
  }

  /**
   * Parameterized constructor that initializes the instance from a {@code name} and {@code
   * typeName}.
   *
   * @param name Name of the variable.
   * @param type Type of the reference.
   */
  protected VariableModel(final String name, final ReferenceType type) {
    this(name, type, VariableModel.DEFAULT_MODIFIABLE);
  }

  /**
   * Parameterized constructor that initializes the instance with all {@code field-members} given.
   *
   * @param name Name of the variable.
   * @param type Type of the variable.
   * @param modifiable Flag that indicates whether the variable is not {@code final}.
   */
  protected VariableModel(final String name, final ReferenceType type, final boolean modifiable) {
    this.name = name;
    this.type = type;
    this.modifiable = modifiable;
  }

  /**
   * Returns the name of the variable.
   *
   * @return The variables name.
   */
  public final String getName() {
    return this.name;
  }

  /**
   * Returns the type of the variable.
   *
   * @return The variables type.
   */
  public final ReferenceType getType() {
    return this.type;
  }

  /**
   * Returns a flag that indicates whether the variable is not {@code final}.
   *
   * @return is the variable not {@code final} ?
   */
  public final boolean isModifiable() {
    return this.modifiable;
  }

  @Override
  public void writeToContext(final GenerationContext context) {
    if (!this.modifiable) {
      context.getBuffer().write("final ");
    }

    context.getBuffer().write(format("{0} {1}", this.type.getTypeName(), this.name));
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", this.name)
        .add("typeName", this.type)
        .add("modifiable", this.modifiable)
        .toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.type, this.modifiable);
  }

  @Override
  public boolean equals(final Object other) {
    return ObjectChecks.equalsDefinitely(this, other).orElseGet(() -> deepEquals(other));
  }

  /**
   * Compares the attributes of both classes.
   *
   * @param other Instance who's attributes are compared to those of the invoked instance.
   * @return Whether the attributes of both instances are equal.
   */
  private boolean deepEquals(final Object other) {
    if (!(other instanceof VariableModel)) {
      return false;
    }

    final VariableModel otherVariable = (VariableModel) other;
    return (this.name.equals(otherVariable.name))
        && (this.type.equals(otherVariable.type))
        && (this.modifiable == otherVariable.modifiable);
  }
  /**
   * Factory method that creates an {@code {@link VariableModel }} from a {@code name}.
   *
   * @param name Name of the variable.
   * @return New instance of an {@code {@link VariableModel }}.
   */
  public static VariableModel create(final String name) {
    Preconditions.checkNotNull(name);

    return new VariableModel(name);
  }

  /**
   * Factory method that creates an {@code {@link VariableModel }} from a {@code name} and {@code
   * typeName}.
   *
   * @param name Name of the variable.
   * @param type Type of the variables.
   * @return New instance of an {@code {@link VariableModel }}.
   */
  public static VariableModel create(final String name, final ReferenceType type) {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(type);

    return new VariableModel(name, type);
  }

  /**
   * Factory method that creates an {@code {@link VariableModel }} with all arguments.
   *
   * @param name Name of the variable.
   * @param type Type of the variable.
   * @param modifiable Indicates whether the variable is not final.
   * @return New instance of an {@code {@link VariableModel }}.
   */
  public static VariableModel create(
      final String name, final ReferenceType type, final boolean modifiable) {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(type);

    return new VariableModel(name, type, modifiable);
  }
}
