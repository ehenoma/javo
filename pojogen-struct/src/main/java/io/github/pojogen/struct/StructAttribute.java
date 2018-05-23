package io.github.pojogen.struct;

import java.util.Objects;

public class StructAttribute {

  private final String name;
  private final String typeName;
  private final boolean constant;

  public StructAttribute() {
    this("undefined");
  }

  public StructAttribute(final StructAttribute attribute) {
    this(attribute.name, attribute.typeName, attribute.constant);
  }

  public StructAttribute(final String name) {
    this(name, Object.class.getSimpleName());
  }

  public StructAttribute(final String name, final String typeName) {
    this(name, typeName, false);
  }

  public StructAttribute(
      final String name,
      final String typeName,
      final boolean constant) {
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

    return contentBuilder.append(this.name)
        .append(": ")
        .append(typeName)
        .toString();
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

}
