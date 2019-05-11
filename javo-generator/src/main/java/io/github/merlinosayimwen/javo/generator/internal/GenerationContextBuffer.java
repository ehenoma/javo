// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal;

import static com.google.common.base.Strings.nullToEmpty;
import static java.util.Objects.isNull;

import java.util.Objects;
import javax.annotation.Nullable;

public class GenerationContextBuffer {

  public static final String NULL_VALUE = "NULL";

  private final StringBuilder delegate;
  private String newLinePrefix;

  private GenerationContextBuffer() {
    this(new StringBuilder());
  }

  private GenerationContextBuffer(final StringBuilder delegate) {
    this(delegate, "");
  }

  private GenerationContextBuffer(final StringBuilder delegate, final String newLinePrefix) {
    this.delegate = delegate;
    this.newLinePrefix = newLinePrefix;
  }

  public void write(@Nullable final Object object) {
    if (isNull(object)) {
      this.writeNull();
      return;
    }

    this.write(object.toString());
  }

  public void write(@Nullable final CharSequence sequence) {
    if (isNull(sequence)) {
      this.writeNull();
      return;
    }

    this.delegate.append(sequence);
  }

  private void writeNull() {
    this.write(GenerationContextBuffer.NULL_VALUE);
  }

  public void writeLine() {
    this.delegate.append('\n');
    this.delegate.append(nullToEmpty(this.newLinePrefix));
  }

  public void writeLine(final Object object) {
    if (isNull(object)) {
      this.writeNull();
      this.writeLine();
      return;
    }

    this.writeLine(object.toString());
  }

  public void writeLine(final CharSequence sequence) {
    if (isNull(sequence)) {
      this.writeNull();
      this.writeLine();
    }

    this.write(sequence);
    this.writeLine();
  }

  public void setNewLinePrefix(String newLinePrefix) {
    this.newLinePrefix = newLinePrefix;
  }

  @Override
  public String toString() {
    return this.delegate.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.delegate, nullToEmpty(this.newLinePrefix));
  }

  @Override
  public boolean equals(Object checkTarget) {
    return ObjectChecks.equalsDefinitely(this, checkTarget)
        .orElseGet(() -> deepEquals(checkTarget));
  }

  private boolean deepEquals(final Object checkTarget) {
    if (!(checkTarget instanceof GenerationContextBuffer)) {
      return false;
    }

    final GenerationContextBuffer otherBuffer = (GenerationContextBuffer) checkTarget;
    // Using Object#equals(Object,Object) because of the chance that the {newLinePrefix} is null.
    return Objects.equals(this.newLinePrefix, otherBuffer.newLinePrefix)
        && this.delegate.equals(otherBuffer.delegate);
  }

  public static GenerationContextBuffer create() {
    return new GenerationContextBuffer();
  }

  public static GenerationContextBuffer create(final String newLinePrefix) {

    return new GenerationContextBuffer(new StringBuilder(), nullToEmpty(newLinePrefix));
  }
}
