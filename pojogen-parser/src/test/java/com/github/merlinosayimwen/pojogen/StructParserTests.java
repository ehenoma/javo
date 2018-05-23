package com.github.merlinosayimwen.pojogen;

import io.github.pojogen.parser.StructParser;
import io.github.pojogen.parser.StructParserException;
import io.github.pojogen.parser.StructParserFactory;
import io.github.pojogen.struct.Struct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StructParserTests {

  public static void main(final String... ignoredArguments) {
    final StructParser parser = new StructParserFactory().create();

    try (final InputStream resource = new FileInputStream(new File(
        Thread.currentThread().getClass().getClassLoader().getResource("example.struct")
            .getPath()))) {
      final Struct struct = parser.parseOne(resource);
      System.out.println(struct);
    } catch (final IOException resourceFailure) {
      System.out.println("Could not read resource {example.struct}");
    } catch (final StructParserException parsingFailure) {
      parsingFailure.printStackTrace();
    }
  }

}
