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

package io.github.pojogen.generator.util;

import java.util.function.Supplier;

public abstract class ImmutableMemoizingObject {

  private final Supplier<Integer> hashCodeFunction;
  private final Supplier<String> stringRepresentationFunction;

  protected ImmutableMemoizingObject() {
    this.hashCodeFunction = this::calculateHashCodeOnce;
    this.stringRepresentationFunction = this::generateStringRepresentationOnce;
  }

  protected abstract int calculateHashCodeOnce();

  protected abstract String generateStringRepresentationOnce();

  @Override
  public final int hashCode() {
    return this.hashCodeFunction.get();
  }

  @Override
  public final String toString() {
    return this.stringRepresentationFunction.get();
  }
}
