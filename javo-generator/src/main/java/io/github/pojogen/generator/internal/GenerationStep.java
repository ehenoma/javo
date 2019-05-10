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

/**
 * A GenerationStep is writing structured text to the {@code GenerationContext}.
 *
 * <p>It is an abstraction of classes which mostly represent members of Java classes. When
 * generating a Pojo through the {@code PojoGenerator Implementation}, all defined steps are called
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
