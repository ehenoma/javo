// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.util;

import java.util.Objects;
import java.util.Observable;

import com.google.common.base.Preconditions;


public class ObservableInt extends Observable implements MutableInt {
  private int value;

  private ObservableInt(int value) {
    this.value = value;
  }

  @Override
  public void incrementBy(int amount) {
    this.setValue(this.value + amount);
  }

  @Override
  public void decrementBy(int amount) {
    this.setValue(this.value - amount);
  }

  @Override
  public int getValue() {
    return this.value;
  }

  @Override
  public void setValue(int value) {
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
    return Objects.hash(value);
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (object instanceof Number) {
      return ((Number)object).intValue() == value;
    }
    if (!(object instanceof ObservableInt)) {
      return false;
    }
    return deepEquals((ObservableInt)object);
  }

  private boolean deepEquals(ObservableInt value) {
    return this.value == value.value;
  }
  
  public static ObservableInt from(int value) {
    return new ObservableInt(value);
  }

  public static ObservableInt copyOf(ObservableInt value) {
    Preconditions.checkNotNull(value);

    return new ObservableInt(value.getValue());
  }

  public static ObservableInt create() {
    return new ObservableInt(0);
  }
  
}
