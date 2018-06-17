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

package io.github.pojogen.generator;

import io.github.pojogen.struct.Struct;
import io.github.pojogen.struct.StructAttribute;
import java.util.Arrays;

public final class GeneratorTests {

  public static void main(final String... ignoredArguments) {
    final PojoGenerator generator = PojoGeneratorFactory.create().getInstance();

    final Struct model =
        Struct.create(
            "Test",
            Arrays.asList(
                StructAttribute.create("id", "Long", true),
                StructAttribute.create("name", "String")));

    System.out.println(generator.generate(model));
  }
}
