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

import com.google.common.base.Preconditions;
import java.util.Optional;

class InternalImportStatementGenerator {

  private static final String IGNORED_CLASS_PACKAGE_PREFIX = "java.lang";

  Optional<String> generateImportStatementForClass(final String typeName) {
    return this.resolveClassInCurrentLoader(typeName)
        .filter(this::needsToBeImported)
        .map(this::generateImportStatementForName);
  }

  private String generateImportStatementForName(final Class<?> entry) {
    Preconditions.checkNotNull(entry);

    return String.format("import %s;", entry.getName());
  }

  private Optional<Class<?>> resolveClassInCurrentLoader(final String typeName) {
    try {
      return Optional.of(Class.forName(typeName));
    } catch (final ClassNotFoundException absentConfirmation) {
      return Optional.empty();
    }
  }

  private boolean needsToBeImported(final Class<?> entry) {
    Preconditions.checkNotNull(entry);

    return !entry.getName().startsWith(IGNORED_CLASS_PACKAGE_PREFIX);
  }
}
