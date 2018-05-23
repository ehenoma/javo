package com.github.merlinosayimwen.pojogen;

import java.util.Collection;
import java.util.Scanner;

import com.google.common.base.Preconditions;

import io.github.pojogen.parser.StructParser;
import io.github.pojogen.parser.StructParserException;
import io.github.pojogen.parser.StructParserFactory;
import io.github.pojogen.struct.Struct;

public class StructParserTests {

  private static final String INPUT_READ_STOP_COMMAND = "/end";

  public static void main(final String... ignoredArguments) {
    final StructParser parser = new StructParserFactory().create();

    try (final Scanner userInput = new Scanner(System.in)) {
      final String prototype = StructParserTests.readInputUntil(userInput, INPUT_READ_STOP_COMMAND);
      final Collection<Struct> parsedStructures = parser
          .parse(prototype);

      parsedStructures.forEach(System.out::println);
    } catch (final StructParserException parsingFailure) {
      parsingFailure.printStackTrace();
    }
  }

  private static String readInputUntil(final Scanner inputSource, final String stopCommand) {
    Preconditions.checkArgument(inputSource.hasNextLine(), "The {inputSource} is empty");

    final StringBuilder readInput = new StringBuilder();
    for (String line = inputSource.nextLine(); line != null;
        line = inputSource.nextLine()) {
      if (stopCommand.equalsIgnoreCase(line)) {
        break;
      }

      readInput.append(line).append('\n');
    }

    return readInput.toString();
  }

}
