package io.github.pojogen.generator.internal;

import java.util.Objects;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 *
 *
 * @author Merlin Osayimwen
 * @see InternalClassMemberModel
 * @see InternalGenerationContext
 * @see InternalGenerationStep
 * @since 1.0
 */
final class InternalFieldModel extends InternalClassMemberModel {

  private static final String DEFAULT_TYPENAME = Object.class.getSimpleName();

  private final String name;
  private final String typeName;
  private final boolean constant;

  private InternalFieldModel(final String name) {
    this(name, DEFAULT_TYPENAME);
  }

  private InternalFieldModel(final String name, final String typeName) {
    this(name, typeName, AccessModifier.PACKAGE_PRIVATE);
  }

  private InternalFieldModel(
      final String name,
      final String typeName,
      final AccessModifier constant) {
    this(name, typeName, constant, false);
  }

  private InternalFieldModel(
      final String name,
      final String typeName,
      final AccessModifier accessModifier,
      final boolean constant) {
    super(accessModifier);
    this.name = name;
    this.typeName = typeName;
    this.constant = constant;
  }

  String getName() {
    return this.name;
  }

  String getTypeName() {
    return this.typeName;
  }

  boolean isConstant() {
    return constant;
  }

  @Override
  public void writeToContext(final InternalGenerationContext buffer) {
    buffer.write(super.accessModifier.getCodeRepresentation());
    buffer.write(' ');

    if (this.constant) {
      buffer.write("final ");
    }

    buffer.writeFormatted("{0} {1};", this.typeName, this.name);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", this.name)
        .add("typeName", this.typeName)
        .add("accessModifier", this.accessModifier)
        .add("constant", this.constant)
        .toString();
  }

  @Override
  public boolean equals(final Object other) {
    if (other == null) {
      return false;
    }

    if (!(other instanceof InternalFieldModel)) {
      return false;
    }

    final InternalFieldModel otherField = (InternalFieldModel) other;
    return (this.name.equals(otherField.name))
        && (this.typeName.equals(otherField.typeName))
        && (this.accessModifier.equals(otherField.accessModifier))
        && (this.constant == otherField.constant);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.typeName, this.accessModifier, this.constant);
  }


  /**
   * Factory method that creates a new {@code {@link InternalFieldModel }} instance.
   *
   * @param name Name of the constructed {@code {@link InternalFieldModel }}.
   * @return New instance of a {@code {@link InternalFieldModel }}.
   */
  static InternalFieldModel from(final String name) {
    Preconditions.checkNotNull(name);

    return new InternalFieldModel(name);
  }

}
