// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.example.console;

import com.google.common.util.concurrent.Uninterruptibles;
import io.github.pojogen.generator.PojoGenerator;
import io.github.pojogen.generator.PojoGeneratorFactory;
import io.github.pojogen.parser.StructParser;
import io.github.pojogen.parser.StructParserException;
import io.github.pojogen.parser.StructParserFactory;
import io.github.merlinosayimwen.pojogen.struct.Struct;
import java.util.logging.Level;
import java.util.logging.Logger;

final class ConsoleExample {

  private static final Logger LOG;

  static {
    LOG = Logger.getLogger("Example");
    LOG.setLevel(Level.INFO);
  }

  public static void main(final String... ignoredArguments) {
    final ConsoleInputAcceptor inputLineListener =
        new ConsoleInputAcceptor(System.in, ConsoleExample::tryParseAndGenerate);

    inputLineListener.start();
    logInfo("The ConsoleExample has been started.");

    Uninterruptibles.joinUninterruptibly(inputLineListener);
    logInfo("The ConsoleExample has been finished.");
  }

  private static void tryParseAndGenerate(final String raw) {
    try {
      ConsoleExample.parseAndGenerate(raw);
    } catch (final StructParserException parsingFailure) {
      if (LOG.isLoggable(Level.WARNING)) {
        LOG.log(Level.WARNING, "Failed to parse the struct.", parsingFailure);
      }
    }
  }

  private static void parseAndGenerate(final String raw) throws StructParserException {
    logInfo("Starting to parse the input struct-blueprint.");

    final StructParser parser = StructParserFactory.create().getInstance();
    final Struct parsedStruct = parser.parseOne(raw);
    if (parsedStruct == null) {
      if (LOG.isLoggable(Level.SEVERE)) {
        LOG.severe("Failed to parse the input");
      }
      return;
    }

    logInfo("Finished parsing the struct %s successfully.", parsedStruct.getName());
    logInfo("Starting the Pojo generation process");

    final PojoGenerator generator = PojoGeneratorFactory.create().getInstance();
    final String generatedJavaClass = generator.generate(parsedStruct);

    logInfo("Finished generating a Pojo successfully");
    logInfo("\nGenerated Pojo:%n%s%n", generatedJavaClass);
  }

  private static void logInfo(final String message, final Object... formattingArguments) {
    if (LOG.isLoggable(Level.INFO)) {
      LOG.info(String.format(message, formattingArguments));
    }
  }
}
