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

package io.github.pojogen.example.file;

import io.github.pojogen.generator.PojoGenerator;
import io.github.pojogen.generator.PojoGeneratorFactory;
import io.github.pojogen.parser.StructParser;
import io.github.pojogen.parser.StructParserException;
import io.github.pojogen.parser.StructParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileExample {

  public static void main(final String... ignoredArguments) throws StructParserException {
    try (final Scanner scanner = new Scanner(System.in)) {
      while (scanner.hasNextLine()) {
        readFileAtPath(scanner.nextLine());
      }
    }
  }

  private static void readFileAtPath(final String path) throws StructParserException {
    final File fileAtPath = new File(path);
    final String fileBody = FileExample.readFileBody(fileAtPath);

    final StructParser parser = StructParserFactory.create().getInstance();
    final PojoGenerator generator = PojoGeneratorFactory.create().getInstance();

    parser.parse(fileBody).stream().map(generator::generate).forEach(System.out::println);
  }

  private static String readFileBody(final File file) {

    try (final Scanner fileScanner = new Scanner(file)) {
      final StringBuilder bodyBuilder = new StringBuilder();

      while (fileScanner.hasNextLine()) {
        bodyBuilder.append(fileScanner.nextLine());
      }

      return bodyBuilder.toString();
    } catch (final IOException ioFailure) {
      ioFailure.printStackTrace();
    }

    return "ERROR(NO_BODY)";
  }
}
