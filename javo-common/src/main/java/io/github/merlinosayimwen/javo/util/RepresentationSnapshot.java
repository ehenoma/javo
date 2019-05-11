// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;

import static com.google.common.base.Suppliers.memoize;

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
