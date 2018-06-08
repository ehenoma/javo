package io.github.pojogen.struct;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public final class Struct {

  private final String name;
  private final Collection<StructAttribute> attributes;
  private final boolean constant;

  private Struct(final String name) {
    this(name, Collections.emptyList());
  }

  private Struct(final String name, final Collection<StructAttribute> attributes) {
    this(name, attributes, false);
  }

  private Struct(
      final String name, final Collection<StructAttribute> attributes, final boolean constant) {

    this.name = name;
    this.attributes = ImmutableList.copyOf(attributes);
    this.constant = constant;
  }

  public String getName() {
    return this.name;
  }

  public Collection<StructAttribute> getAttributes() {
    return this.attributes;
  }

  public boolean isConstant() {
    return constant;
  }

  @Override
  public String toString() {
    final ToStringHelper representationBuilder =
        MoreObjects.toStringHelper(this)
            .add("name", this.name)
            .add("const", this.constant)
            .add("attributes", this.attributes.size());

    return representationBuilder.toString();
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

  public static Struct create(final String name) {
    Preconditions.checkNotNull(name);

    return Struct.create(name, Collections.emptyList());
  }

  public static Struct create(final String name, final Collection<StructAttribute> attributes) {
    return Struct.create(name, attributes, false);
  }

  public static Struct create(
      final String name, final Collection<StructAttribute> attributes, final boolean constant) {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(attributes);

    return new Struct(name, attributes, constant);
  }

  public static Struct copyOf(final Struct struct) {
    Preconditions.checkNotNull(struct);

    return new Struct(struct.name, struct.attributes, struct.constant);
  }
}
