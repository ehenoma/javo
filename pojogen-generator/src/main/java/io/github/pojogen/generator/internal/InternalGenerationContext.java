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

import com.google.common.base.Preconditions;
import io.github.pojogen.generator.GenerationProfile;
import java.text.MessageFormat;

/**
 * @author Merlin Osayimwen
 * @see InternalGenerationStep
 * @since 1.0
 */
final class InternalGenerationContext implements AutoCloseable {

  private final GenerationProfile profile;
  private final StringBuilder dedicatedBuffer;
  private final String depthPrefix;
  private short currentDepth;

  InternalGenerationContext(final GenerationProfile profile, final String depthPrefix) {
    this.dedicatedBuffer = new StringBuilder();
    this.depthPrefix = depthPrefix;
    this.profile = profile;
  }

  InternalGenerationContext write(final char character) {
    this.dedicatedBuffer.append(character);
    return this;
  }

  InternalGenerationContext write(final CharSequence characters) {
    Preconditions.checkNotNull(characters);

    this.dedicatedBuffer.append(characters);
    return this;
  }

  InternalGenerationContext write(final Object object) {
    Preconditions.checkNotNull(object);

    this.dedicatedBuffer.append(object.toString());
    return this;
  }

  InternalGenerationContext writeFormatted(final String rawString, final Object... arguments) {
    return this.write(MessageFormat.format(rawString, arguments));
  }

  InternalGenerationContext writeLineBreak() {
    return this.write(generateLinePrefix());
  }

  InternalGenerationContext writeLineBreak(final int repeats) {
    for (int iteration = 0; iteration < repeats; iteration++) {
      this.writeLineBreak();
    }

    return this;
  }

  InternalGenerationContext increaseDepth() {
    this.currentDepth += 1;
    return this;
  }

  InternalGenerationContext increaseDepthBy(final int amount) {
    this.currentDepth += amount;
    return this;
  }

  InternalGenerationContext decreaseDepth() {
    this.currentDepth -= 1;
    return this;
  }

  InternalGenerationContext decreaseDepthBy(final int amount) {
    this.currentDepth -= amount;
    return this;
  }

  String produceResult() {
    return this.dedicatedBuffer.toString();
  }

  String generateLinePrefix() {
    final StringBuilder prefixBuilder = new StringBuilder(this.currentDepth * depthPrefix.length());
    for (int iteration = 0; iteration < this.currentDepth; iteration++) {
      prefixBuilder.append(this.depthPrefix);
    }

    return prefixBuilder.toString();
  }

  GenerationProfile profile() {
    return this.profile;
  }

  @Override
  public void close() {
    // Discards the buffer.
    this.dedicatedBuffer.setLength(0);
  }
}
