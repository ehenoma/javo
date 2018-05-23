package io.github.pojogen.parser.internal;

import io.github.pojogen.parser.StructParser;
import java.util.function.Supplier;

public final class InternalAccess {

  private InternalAccess() {
  }

  public static Supplier<StructParser> internalStructParserSupply() {
    return InternalStructParser::new;
  }

}
