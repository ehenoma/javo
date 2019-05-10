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

package io.github.pojogen.example.console;

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
