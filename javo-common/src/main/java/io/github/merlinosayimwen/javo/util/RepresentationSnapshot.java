// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.util;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

import com.google.common.base.Preconditions;

public final class RepresentationSnapshot {
  private IntSupplier hashCode;
  private Supplier<String> stringRepresentation;

  private RepresentationSnapshot(
      IntSupplier hashCode,
      Supplier<String> stringRepresentation) {

    this.hashCode = hashCode;
    this.stringRepresentation = stringRepresentation;
  }

  public int getHashCode() {
    return this.hashCode.getAsInt();
  }

  public String getStringRepresentation() {
    return this.stringRepresentation.get();
  }

  public static RepresentationSnapshot create(
      IntSupplier hashCode,
      Supplier<String> stringRepresentation) {

    Preconditions.checkNotNull(hashCode);
    Preconditions.checkNotNull(stringRepresentation);
    return new RepresentationSnapshot(
      IntSuppliers.memoize(hashCode),
      MoreSuppliers.memoize(stringRepresentation));
  }

  public static RepresentationSnapshot take(Object instance) {
    Preconditions.checkNotNull(instance);

    return RepresentationSnapshot.create(instance::hashCode, instance::toString);
  }
}
