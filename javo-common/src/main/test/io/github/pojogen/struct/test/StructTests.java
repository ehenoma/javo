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

package io.github.pojogen.struct.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import io.github.pojogen.struct.Struct;
import io.github.pojogen.struct.StructAttribute;
import org.junit.Before;
import org.junit.Test;

public final class StructTests {

  private Struct struct;
  private Struct structCopy;

  @Before
  public void initialize() {
    this.struct =
        Struct.newBuilder()
            .withName("Person")
            .addAttribute(StructAttribute.create("name", "Name"))
            .addAttribute(StructAttribute.create("birthDate", "long"))
            .create();
    this.structCopy = Struct.copyOf(struct);
  }

  @Test
  public void testBuilder() {
    assertThat(this.struct, notNullValue());
    assertThat(this.struct.getAttributes().count(), is(2L));
    assertThat(this.struct.getName(), is("Person"));
  }

  @Test
  public void testCopyMethod() {
    assertThat(this.structCopy, not(sameInstance(this.struct)));
  }

  /**
   * Result of every representative method needs to be the same every time it is called. The Struct
   * class is a value object and therefore immutable.
   */
  @Test
  public void testObjectMethodsConsistency() {
    assertThat(this.struct, is(this.struct));
    assertThat(this.struct.toString(), is(this.struct.toString()));
    assertThat(this.struct.hashCode(), is(this.struct.hashCode()));
  }

  @Test
  public void testObjectMethodsDeeply() {
    assertThat(this.struct, is(this.structCopy));
    assertThat(this.struct.toString(), is(this.structCopy.toString()));
    assertThat(this.struct.hashCode(), is(this.structCopy.hashCode()));
  }
}
