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

import static io.github.pojogen.struct.util.ObjectChecks.equalsDefinitely;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import io.github.pojogen.struct.StructAttribute;
import java.util.Optional;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

public final class ObjectChecksTests {

  private Object firstOperand;
  private Object secondOperand;
  private Object thirdOperand;

  @Before
  public void initialize() {
    this.firstOperand = StructAttribute.create("test", "One");
    this.secondOperand = StructAttribute.create("test", "Two");
    this.thirdOperand = null;
  }

  @Test
  public void unequalDoNotMatch() {
    final Optional<Boolean> firstEqualsFirst = equalsDefinitely(firstOperand, firstOperand);
    final Optional<Boolean> firstEqualsSecond = equalsDefinitely(firstOperand, secondOperand);
    final Optional<Boolean> firstEqualsThird = equalsDefinitely(firstOperand, thirdOperand);

    assertThat(firstEqualsFirst, CoreMatchers.notNullValue());
    assertThat(firstEqualsFirst, is(Optional.of(true)));
    assertThat(firstEqualsSecond, is(Optional.empty()));
    assertThat(firstEqualsThird, is(Optional.of(false)));
  }
}
