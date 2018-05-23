package io.github.pojogen.parser;

public final class StructParserException extends Exception {

  private StructParserException(final Throwable cause) {
    super(cause);
  }

  private StructParserException(final String message) {
    super(message);
  }

  public static StructParserException create() {
    return new StructParserException("Exception occurred while parsing the source");
  }

  public static StructParserException createWithCause(final Throwable cause) {
    return new StructParserException(cause);
  }

  public static StructParserException createWithMessage(final String message) {
    return new StructParserException(message);
  }

}
