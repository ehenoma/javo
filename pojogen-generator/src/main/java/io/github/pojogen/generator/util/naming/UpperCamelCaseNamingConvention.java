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

package io.github.pojogen.generator.util.naming;

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
