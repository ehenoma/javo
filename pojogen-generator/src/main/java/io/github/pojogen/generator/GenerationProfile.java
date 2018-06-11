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

package io.github.pojogen.generator;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Stream;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import io.github.pojogen.generator.util.ImmutableMemoizingObject;

/**
 * Lists settings and preferences or a {@code pojo generation}.
 *
 * <p>The {@code PojoGenerator} allows to enter some preferences on how things should be generated
 * internally. Those are either in the form of a {@code GenerationFlag} which is providing
 * fundamental configurability or {@code custom properties} which are there for finer preferences.
 *
 * @author Merlin Osayimwen
 * @since 1.0
 * @see ImmutableMemoizingObject
 * @see GenerationFlag
 * @see PojoGenerator
 */
public final class GenerationProfile extends ImmutableMemoizingObject {

  /** Collection of flags set for the generation. */
  private final Collection<GenerationFlag> flags;

  /** No-args constructor of the GenerationProfile class. */
  private GenerationProfile() {
    this(Collections.emptyList());
  }

  /**
   * Parameterized constructor that creates a GenerationProfile with zero ore more flags.
   *
   * @param flags Zero ore more flags set for the generation.
   */
  private GenerationProfile(final Iterable<GenerationFlag> flags) {
    this.flags = Sets.newEnumSet(flags, GenerationFlag.class);
  }

  /**
   * Gets whether the given {@code flag} is contained in the profiles flags.
   *
   * @param flag Flag who's presence in the profiles flags is to be tested.
   * @return Whether the {@code flag} is contained in the profiles flags.
   */
  public boolean hasFlag(final GenerationFlag flag) {
    return this.flags.contains(flag);
  }

  /**
   * Gets a stream of all flags configured in the profile.
   *
   * <p>Since the profiles flags may not be modified after creation, using a stream to analysing
   * them is good practice and safer.
   *
   * @return Stream of all flags configured in the profile.
   */
  public Stream<GenerationFlag> streamFlags() {
    return this.flags.stream();
  }

  public Iterable<GenerationFlag> getFlags() {
    return this.flags;
  }

  @Override
  protected String generateStringRepresentationOnce() {
    return MoreObjects.toStringHelper(this)
        .add("flags", "{" + Joiner.on(',').join(this.flags) + "}")
        .toString();
  }

  @Override
  public boolean equals(final Object checkTarget) {
    if (this == checkTarget) {
      return true;
    }

    if (checkTarget == null) {
      return false;
    }

    if (!(checkTarget instanceof GenerationProfile)) {
      return false;
    }

    final GenerationProfile otherProfile = (GenerationProfile) checkTarget;
    return Objects.deepEquals(this.flags.toArray(), otherProfile.flags.toArray());
  }

  @Override
  protected int calculateHashCodeOnce() {
    return this.streamFlags().mapToInt(GenerationFlag::hashCode).map(entry -> entry * 32).sum();
  }

  public static final class Builder {

    private Collection<GenerationFlag> flags;

    private Builder() {
      this.flags = Sets.newHashSet();
    }

    public Builder addFlag(final GenerationFlag flag) {
      this.flags.add(flag);
      return this;
    }

    public GenerationProfile create() {
      return GenerationProfile.create(this.flags);
    }

    public static final Builder newBuilder() {
      return new Builder();
    }
  }

  public static GenerationProfile create() {
    return new GenerationProfile();
  }

  public static GenerationProfile create(final Iterable<GenerationFlag> flags) {
    Preconditions.checkNotNull(flags);

    return new GenerationProfile(flags);
  }
}
