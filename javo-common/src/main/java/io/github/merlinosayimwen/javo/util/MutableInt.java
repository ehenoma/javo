// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.util;

public interface MutableInt {

  int getValue();

  void setValue(final int value);

  default void incrementByOne() {
    this.incrementBy(1);
  }

  void incrementBy(final int value);

  default void decrementByOne() {
    this.decrementBy(1);
  }

  void decrementBy(final int value);
}
