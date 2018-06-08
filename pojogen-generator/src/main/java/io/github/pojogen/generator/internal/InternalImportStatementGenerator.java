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
