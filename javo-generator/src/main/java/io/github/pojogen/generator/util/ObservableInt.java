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

package io.github.pojogen.generator.util;

import com.google.common.base.Preconditions;
import io.github.merlinosayimwen.pojogen.struct.util.ObjectChecks;
import java.util.Observable;

public class ObservableInt extends Observable implements MutableInt {

  private int value;

  private ObservableInt() {
    this(0);
  }

  private ObservableInt(final int value) {
    this.value = value;
  }

  @Override
  public void incrementBy(final int amount) {
    this.setValue(this.value + amount);
  }

  @Override
  public void decrementBy(final int amount) {
    this.setValue(this.value - amount);
  }

  @Override
  public int getValue() {
    return this.value;
  }

  @Override
  public void setValue(final int value) {
    this.value = value;
    this.setChanged();
    this.notifyObservers(this.value);
  }

  @Override
  public String toString() {
    return String.valueOf(this.value);
  }

  @Override
  public int hashCode() {
    return this.value * 32;
  }

  @Override
  public boolean equals(final Object other) {
    return ObjectChecks.equalsDefinitely(this, other).orElse(deepEquals(other));
  }

  private boolean deepEquals(final Object other) {
    if (!(other instanceof ObservableInt)) {
      if (!(other instanceof Number)) {
        return false;
      }

      return this.value == ((Number) other).intValue();
    }

    return this.value == ((ObservableInt) other).value;
  }
  
  public static ObservableInt from(final int value) {
    return new ObservableInt(value);
  }

  public static ObservableInt copyOf(final ObservableInt value) {
    Preconditions.checkNotNull(value);

    return new ObservableInt(value.getValue());
  }

  public static ObservableInt create() {
    return new ObservableInt();
  }
  
}
