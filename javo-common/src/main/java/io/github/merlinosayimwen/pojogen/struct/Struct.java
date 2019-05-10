// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style license that can be
// found in the LICENSE file.

package io.github.merlinosayimwen.pojogen.struct;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Stream;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import io.github.merlinosayimwen.pojogen.struct.util.MorePreconditions;

/**
 * Value-Object representation of a Struct.
 * <p>
 * The struct class is holding information about a defined struct which follows
 * pojogen's struct definition format. It may be parsed or simply created and is used
 * by the generator to generate a Java file. Instances of this class are immutable.
 *
 * @see StructAttribute
 */
public final class Struct {

  public enum Modifier {
    IMMUTABLE
  }

  /** Name of the struct. */
  private String name;

  /** The struct's attributes. */
  private Collection<StructAttribute> attributes;

  /** Set of modifiers that influence the generated java class. */
  private Collection<Modifier> modifiers;

  /**
   * Parameterized constructor that initializes a struct with all available arguments.
   *
   * @param name The name that the struct will have.
   * @param attributes Set of attributes which will be given to the struct.
   * @param modifiers Set of modifiers that influence the generated java class.
   */
  private Struct(
      String name, Iterable<StructAttribute> attributes, Collection<Modifier> modifiers) {

    this.name = name;
    this.attributes = ImmutableList.copyOf(attributes);
    this.modifiers = modifiers;
  }

  /**
   * Returns the struct's name.
   *
   * @return Name of the struct.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns a stream of the struct's attributes.
   *
   * <p>A stream is returned to indicate, that the attributes can not be changed after construction
   * and prevents pitfalls.
   *
   * @return Stream of the structs attributes.
   */
  public Stream<StructAttribute> getAttributes() {
    return attributes.stream();
  }

  public boolean hasModifier(Modifier modifier) {
    return modifiers.contains(modifier);
  }

  /**
   * Returns whether the struct is effectively immutable.
   *
   * <p>This is either {@code true}, if the {@code struct} is declared {@code const} or every of its
   * {@code attributes} are.
   *
   * @return Whether the struct is immutable.
   */
  public boolean isImmutable() {
    if (hasModifier(Modifier.IMMUTABLE)) {
      return true;
    }
    return attributes.stream()
      .allMatch(StructAttribute::isImmutable);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("name", name)
      .add("modifiers",modifiers.size())
      .add("attributes", attributes.size())
      .toString();
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (!(object instanceof Struct)) {
      return false;
    }
    return deepEquals((Struct)object);
  }

  private boolean deepEquals(Struct struct) {
    return this.name.equals(struct.name)
        && Arrays.deepEquals(modifiers.toArray(), struct.attributes.toArray())
        && Arrays.deepEquals(attributes.toArray(), struct.attributes.toArray());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      name,
      Arrays.deepHashCode(attributes.toArray()),
      Arrays.deepHashCode(attributes.toArray())
    );
  }

  /**
   * Creates a struct from just a name.
   *
   * @param name Name that is given to the struct.
   * @return Created struct with the given name and no attributes.
   */
  public static Struct create(String name) {
    return Struct.create(name, Collections.emptyList());
  }

  /**
   * Creates a struct from a name and some attributes.
   *
   * @param name Name that is given to the struct.
   * @param attributes Iterable collection of attributes.
   * @return Created struct with the given name and attributes.
   */
  public static Struct create(String name, Iterable<StructAttribute> attributes) {
    return Struct.create(name, attributes, Collections.emptyList());
  }

  /**
   * Creates a struct with all possible arguments.
   *
   * @param name Name that is given to the struct.
   * @param attributes Iterable collection of attributes.
   * @param modifiers Set of modifiers that influence the generated java class.
   * @return Struct created from the given arguments.
   */
  public static Struct create(
      String name,
      Iterable<StructAttribute> attributes,
      Iterable<Modifier> modifiers) {

    MorePreconditions.checkAllNotNull(attributes);
    MorePreconditions.checkAllNotNull(modifiers);

    return new Struct(
      name,
      Sets.newHashSet(attributes),
      Sets.newHashSet(modifiers)
    );
  }

  public static Builder newBuilder() {
    return new Builder("", Lists.newArrayList(), Lists.newArrayList());
  }

  public static Builder newBuilder(Struct prototype) {
    return new Builder(
      prototype.name,
      Lists.newArrayList(prototype.attributes),
      Lists.newArrayList(prototype.modifiers)
    );
  }

  public static final class Builder {
    private String name;
    private Collection<Modifier> modifiers;
    private Collection<StructAttribute> attributes;

    private Builder(
      String name,
      Collection<StructAttribute> attributes,
      Collection<Modifier> modifiers) {

      this.name = name;
      this.modifiers = modifiers;
      this.attributes = attributes;
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withModifiers(Iterable<Modifier> modifiers) {
      this.modifiers = Lists.newArrayList(modifiers);
      return this;
    }

    public Builder withAttributes(Iterable<StructAttribute> attributes) {
      this.attributes = Lists.newArrayList(attributes);
      return this;
    }

    public Builder addModifier(Modifier modifier) {
      ensureModifiersNotNull();
      modifiers.add(modifier);
      return this;
    }

    public Builder addAttribute(StructAttribute attribute) {
      ensureAttributesNotNull();
      attributes.add(attribute);
      return this;
    }

    private void ensureAttributesNotNull() {
      if (this.attributes == null) {
        this.attributes = Lists.newArrayList();
      }
    }

    private void ensureModifiersNotNull() {
      if (this.modifiers == null) {
        this.modifiers = Lists.newArrayList();
      }
    }

    public Struct create() {
      Preconditions.checkNotNull(this.name);
      this.ensureAttributesNotNull();
      this.ensureModifiersNotNull();

      return new Struct(
        this.name,
        Sets.newHashSet(this.attributes),
        Sets.newHashSet(this.modifiers)
      );
    }
  }
}
