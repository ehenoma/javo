package io.github.pojogen.parser;

import io.github.pojogen.parser.internal.InternalAccess;
import java.util.function.Supplier;

public final class StructParserFactory {

  private static Supplier<StructParser> supplier;

  static {
    StructParserFactory.supplier = InternalAccess.internalStructParserSupply();
  }

  public StructParser create() {
    return supplier.get();
  }

}
