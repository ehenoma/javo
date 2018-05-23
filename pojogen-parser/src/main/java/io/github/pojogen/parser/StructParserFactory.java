package io.github.pojogen.parser;

import java.util.function.Supplier;

import io.github.pojogen.parser.internal.InternalAccess;

public final class StructParserFactory {

  private static Supplier<StructParser> supplier;

  static {
    StructParserFactory.supplier = InternalAccess.internalStructParserSupply();
  }

  public StructParser create() {
    return supplier.get();
  }

}
