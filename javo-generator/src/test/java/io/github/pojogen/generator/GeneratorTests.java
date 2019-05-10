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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;

import io.github.pojogen.generator.internal.GenerationContext;
import io.github.merlinosayimwen.pojogen.struct.Struct;
import io.github.merlinosayimwen.pojogen.struct.StructAttribute;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;

public final class GeneratorTests {

  private PojoGenerator generator;
  private GenerationProfile profile;

  @Before
  public void initialize() {
    this.generator = PojoGeneratorFactory.create().getInstance();
    this.profile =
        GenerationProfile.create(
            Collections.singleton(GenerationFlag.DEPENDENCY_GUAVA),
            Collections.singletonMap(GenerationContext.PROPERTY_NEW_LINE_PREFIX, " "));
  }

  @Test
  public void testGeneration() {
    assertThat(generator, notNullValue());
    assertThat(profile, notNullValue());

    final Struct model =
        Struct.newBuilder()
            .withName("Person")
            .addAttribute(StructAttribute.create("id", "long", true))
            .addAttribute(StructAttribute.create("names", "<String>", false))
            .create();

    final String pojo = generator.generate(model);

    assertThat(pojo, notNullValue());
    assertThat(pojo, not(""));
  }
}
