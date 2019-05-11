// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.util;

public interface MutableInt {
  int getValue();

  void setValue(int value);

  void incrementBy(int value);

  void decrementBy(int value);

  default void incrementByOne() {
    this.incrementBy(1);
  }

  default void decrementByOne() {
    this.decrementBy(1);
  }
}
