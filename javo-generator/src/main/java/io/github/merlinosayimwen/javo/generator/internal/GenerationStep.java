// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal;

/**
 * A GenerationStep is writing structured text to the {@code GenerationContext}.
 *
 * <p>It is an abstraction of classes which mostly represent members of Java classes. When
 * generating a Pojo through the {@code JavoGenerator Implementation}, all defined steps are called
 * to produce a final Pojo.
 *
 * @see GenerationContext
 * @since 1.0
 */
@FunctionalInterface
public interface GenerationStep {

  /**
   * Writes the {@code steps} representation to the {@code context}.
   *
   * @param context Context that the {@code code representation} is written to.
   */
  void writeToContext(final GenerationContext context);
}
