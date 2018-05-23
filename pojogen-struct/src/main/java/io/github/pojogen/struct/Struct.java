package io.github.pojogen.struct;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public final class Struct {

  private final String name;
  private final Collection<StructAttribute> attributes;
  private final boolean constant;

  public Struct() {
    this("Undefined");
  }

  public Struct(final String name) {
    this(name, Collections.emptyList());
  }

  public Struct(final String name, final Collection<StructAttribute> attributes) {
    this(name, attributes, false);
  }

  public Struct(
      final String name,
      final Collection<StructAttribute> attributes,
      final boolean constant) {
    this.name = name;
    this.attributes = Lists.newLinkedList(attributes);
    this.constant = constant;
  }

  public String getName() {
    return this.name;
  }

  public Collection<StructAttribute> getAttributes() {
    return ImmutableList.copyOf(this.attributes);
  }

  public boolean isConstant() {
    return constant;
  }

  @Override
  public String toString() {
    final StringBuilder contentBuilder = new StringBuilder();
    if (this.constant) {
      contentBuilder.append("const ");
    }

    contentBuilder.append("struct ")
        .append(name)
        .append(" {\n");

    for (final StructAttribute attribute : this.attributes) {
      contentBuilder.append("  ")
          .append(attribute.toString())
          .append('\n');
    }

    return contentBuilder.append('}').toString();
  }

  @Override
  public boolean equals(final Object other) {
    if (other == null) {
      return false;
    }

    if (!(other instanceof Struct)) {
      return false;
    }

    final Struct otherStruct = (Struct) other;
    return (this.constant == otherStruct.constant)
        && (this.name.equals(otherStruct.name))
        && (this.attributes.equals((otherStruct.attributes)));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.constant, this.attributes);
  }
}
