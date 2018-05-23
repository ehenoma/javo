package io.github.pojogen.struct;

import com.google.common.collect.Sets;
import java.util.Arrays;

public class StructTest {

  public static void main(final String... ignoredArguments) {
    final Struct struct = new Struct("Address",
        Arrays.asList(
            new StructAttribute("streetName", "String"),
            new StructAttribute("houseNumber", "int"),
            new StructAttribute("postalCode", "int")
        ));

    System.out.println(struct);
  }

}
