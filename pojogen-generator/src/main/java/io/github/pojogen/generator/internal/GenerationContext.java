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

package io.github.pojogen.generator.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.github.pojogen.generator.GenerationProfile;
import io.github.pojogen.generator.util.MutableInt;
import io.github.pojogen.generator.util.ObservableInt;
import io.github.pojogen.generator.util.Observers;
import java.util.Objects;

public final class GenerationContext {

  private final GenerationProfile profile;
  private final GenerationContextBuffer buffer;

  private final ObservableInt depth;

  private GenerationContext(final GenerationProfile profile) {
    this(profile, (short) 0);
  }

  private GenerationContext(final GenerationProfile profile, final short baseDepth) {
    this.profile = profile;
    this.buffer = GenerationContextBuffer.create();
    this.depth = ObservableInt.from(baseDepth);
  }

  private void deployObserver() {
    this.depth.addObserver(Observers.fromRunnable(this::updateNewLinePrefix));
  }

  public String finish() {
    return this.buffer.toString();
  }

  private void updateNewLinePrefix() {
    final StringBuilder prefixBuilder = new StringBuilder();
    for (int iteration = 0; iteration < this.depth.getValue(); iteration++) {
      prefixBuilder.append("");
    }

    this.buffer.setNewLinePrefix(prefixBuilder.toString());
  }

  // Just publish the mutable int to provide a smaller interface.
  public MutableInt getDepth() {
    return this.depth;
  }

  public GenerationContextBuffer getBuffer() {
    return this.buffer;
  }

  public GenerationProfile getProfile() {
    return this.profile;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("profile", this.profile)
        .add("buffer", this.buffer)
        .add("depth", this.depth)
        .toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.profile, this.buffer, this.depth);
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  private boolean deepEquals(final Object other) {
    if (!(other instanceof GenerationContext)) {
      return false;
    }

    final GenerationContext otherContext = (GenerationContext) other;
    return this.profile.equals(otherContext.profile)
        && this.buffer.equals(otherContext.buffer)
        && this.depth.equals(otherContext.depth);
  }

  static GenerationContext create(final GenerationProfile profile) {
    Preconditions.checkNotNull(profile);

    // TODO: Get the prefix from the profile.
    final GenerationContext context = new GenerationContext(profile);
    context.deployObserver(); // This escape is prevented.

    return context;
  }
}
