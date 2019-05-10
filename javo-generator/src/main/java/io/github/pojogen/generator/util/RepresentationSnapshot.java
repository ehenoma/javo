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

import static com.google.common.base.Suppliers.memoize;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;

public final class RepresentationSnapshot {

  private final Supplier<Integer> hashCodeSupplier;
  private final Supplier<String> stringRepresentationSupplier;

  private RepresentationSnapshot(
      final Supplier<Integer> hashCodeSupplier,
      final Supplier<String> stringRepresentationSupplier) {

    this.hashCodeSupplier = hashCodeSupplier;
    this.stringRepresentationSupplier = stringRepresentationSupplier;
  }

  public int getHashCode() {
    return this.hashCodeSupplier.get();
  }

  public String getStringRepresentation() {
    return this.stringRepresentationSupplier.get();
  }

  public static RepresentationSnapshot create(
      final Supplier<Integer> hashCodeSupplier, final Supplier<String> toStringSupplier) {

    Preconditions.checkNotNull(hashCodeSupplier);
    Preconditions.checkNotNull(toStringSupplier);
    return new RepresentationSnapshot(memoize(hashCodeSupplier), memoize(toStringSupplier));
  }

  public static RepresentationSnapshot take(final Object instance) {
    Preconditions.checkNotNull(instance);

    return RepresentationSnapshot.create(instance::hashCode, instance::toString);
  }
}
