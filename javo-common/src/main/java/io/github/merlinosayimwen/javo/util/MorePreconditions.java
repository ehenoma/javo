// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.util;

import com.google.common.base.Preconditions;

public final class MorePreconditions {
  private MorePreconditions() {}

  public static void checkAllNotNull(Iterable<?> elements) {
    Preconditions.checkNotNull(elements);
    elements.forEach(Preconditions::checkNotNull);
  }
}
