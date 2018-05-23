package io.github.pojogen.parser.internal;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import io.github.pojogen.parser.StructParser;

public final class InternalAccess {

  private InternalAccess() {
  }

  public static Supplier<StructParser> internalStructParserSupply() {
    return Suppliers.memoize(InternalStructParser::new);
  }

}
