// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.util.naming;

import com.google.common.base.Preconditions;

public final class UpperCamelCaseNamingConvention implements NamingConvention {

  public String apply(final String text) {
    Preconditions.checkNotNull(text);
    if (text.length() == 0) {
      return text;
    }

    if (text.length() == 1) {
      return String.valueOf(text.charAt(0)).toUpperCase();
    }

    return String.valueOf(text.charAt(0)).toUpperCase().concat(text.substring(1));
  }
}
