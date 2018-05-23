package io.github.pojogen.generator.internal;

import java.util.Objects;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * Immutable {@code value object} that is representing a local or global {@code variable}.
 *
 * The {@code {@link InternalVariableModel}} class is used to describe method parameters, local and
 * global variables that can be {@code immutable (final)}. It is open for implementations in order
 * for more explicit objects of type {@code variable}, to build up on this model. An example for an
 * implementation is the {@code {@link InternalFieldModel}} class which has a additional {@code
 * access modifier}. The constructors are accessible from inside the package but using the {@code
 * factory-methods} is highly encouraged; they will do additional safety checks.
 *
 * @author Merlin Osayimwen
 * @see InternalClassModel
 * @see InternalFieldModel
 * @since 1.0
 */
class InternalVariableModel {

  /**
   * The default {typename} that is used as a {fallback value}.
   */
  private static final String DEFAULT_TYPENAME = Object.class.getSimpleName();

  /**
   * The default {modifiable} flag that is used as a {fallback value}.
   */
  private static final boolean DEFAULT_MODIFIABLE = true;

  /**
   * Name of the variable.
   */
  protected final String name;

  /**
   * Name of the variables type.
   */
  protected final String typeName;

  /**
   * Flag that indicates whether the variable is not {@code final}.
   */
  protected final boolean modifiable;

  /**
   * Parameterized constructor that initializes the instance from a {@code name}.
   *
   * @param name Name of the variable.
   */
  protected InternalVariableModel(final String name) {
    this(name, InternalVariableModel.DEFAULT_TYPENAME);
  }

  /**
   * Parameterized constructor that initializes the instance from a {@code name} and {@code
   * typeName}.
   *
   * @param name Name of the variable.
   * @param typeName Name of the variables type.
   */
  protected InternalVariableModel(final String name, final String typeName) {
    this(name, typeName, InternalVariableModel.DEFAULT_MODIFIABLE);
  }

  /**
   * Parameterized constructor that initializes the instance with all {@code field-members} given.
   *
   * @param name Name of the variable.
   * @param typeName Name of the variables type.
   * @param modifiable Flag that indicates whether the variable is not {@code final}.
   */
  protected InternalVariableModel(final String name, final String typeName,
      final boolean modifiable) {
    this.name = name;
    this.typeName = typeName;
    this.modifiable = modifiable;
  }

  /**
   * Returns the name of the variable.
   *
   * @return The variables name.
   */
  final String getName() {
    return this.name;
  }

  /**
   * Returns the type of the variable.
   *
   * @return The variables type.
   */
  final String getTypeName() {
    return this.typeName;
  }

  /**
   * Returns a flag that indicates whether the variable is not {@code final}.
   *
   * @return is the variable not {@code final} ?
   */
  final boolean isModifiable() {
    return this.modifiable;
  }

  @Override
  public final String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", this.name)
        .add("typeName", this.typeName)
        .add("modifiable", this.modifiable)
        .toString();
  }

  @Override
  public final int hashCode() {
    return Objects.hash(this.name, this.typeName, this.modifiable);
  }

  @Override
  public final boolean equals(final Object other) {
    if (other == null) {
      return false;
    }

    if (!(other instanceof InternalVariableModel)) {
      return false;
    }

    final InternalVariableModel otherVariable = (InternalVariableModel) other;
    return (this.name.equals(otherVariable.name))
        && (this.typeName.equals(otherVariable.typeName))
        && (this.modifiable == otherVariable.modifiable);
  }

  /**
   * Factory method that creates an {@code {@link InternalVariableModel}} from a {@code name}.
   *
   * @param name Name of the variable.
   * @return New instance of an {@code {@link InternalVariableModel}}.
   */
  static InternalVariableModel create(final String name) {
    Preconditions.checkNotNull(name);

    return new InternalVariableModel(name);
  }

  /**
   * Factory method that creates an {@code {@link InternalVariableModel}} from a {@code name} and
   * {@code typeName}.
   *
   * @param name Name of the variable.
   * @param typeName Name of the variables type.
   * @return New instance of an {@code {@link InternalVariableModel}}.
   */
  static InternalVariableModel create(final String name, final String typeName) {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(typeName);

    return new InternalVariableModel(name, typeName);
  }

  /**
   * Factory method that creates an {@code {@link InternalVariableModel}} with all arguments.
   *
   * @param name Name of the variable.
   * @param typeName Name of the variables type.
   * @param modifiable Indicates whether the variable is not final.
   * @return New instance of an {@code {@link InternalVariableModel}}.
   */
  static InternalVariableModel create(final String name, final String typeName,
      final boolean modifiable) {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(typeName);

    return new InternalVariableModel(name, typeName, modifiable);
  }

}
