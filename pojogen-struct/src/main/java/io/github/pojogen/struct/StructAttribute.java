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
