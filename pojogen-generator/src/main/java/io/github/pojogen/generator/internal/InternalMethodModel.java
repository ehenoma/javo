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

package io.github.pojogen.generator.internal;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import io.github.pojogen.generator.GenerationFlag;
import java.util.Collection;
import java.util.Iterator;
import java.util.OptionalInt;
import java.util.function.Consumer;

public abstract class InternalMethodModel {

  protected final String returnType;
  protected final String methodName;
  protected final Collection<InternalFieldModel> parameters;

  private InternalMethodModel(
      final String methodName,
      final String returnType,
      final Collection<InternalFieldModel> parameters) {}

  private InternalMethodModel(
      final String methodName,
      final String returnType,
      final Collection<InternalFieldModel> parameters,
      final AccessModifier accessModifier) {

    super(accessModifier);
    this.returnType = returnType;
    this.methodName = methodName;
    this.parameters = ImmutableList.copyOf(parameters);
  }

  @Override
  public void writeToContext(InternalGenerationContext buffer) {
    buffer.append(this.accessModifier.getCodeRepresentation()).append(' ').append(this.returnType);

    if (!Strings.isNullOrEmpty(this.methodName)) {
      buffer.append(' ').append(this.methodName);
    }

    buffer.append('(');

    final Iterator<InternalFieldModel> parameterIterator = parameters.iterator();
    while (parameterIterator.hasNext()) {
      if (buffer.profile().hasFlag(GenerationFlag.MAKE_LOCAL_VARIABLE_FINAL)) {
        buffer.append("final ");
      }

      final InternalFieldModel parameter = parameterIterator.next();
      buffer.append(parameter.getTypeName()).append(' ').append(parameter.getName());

      if (parameterIterator.hasNext()) {
        buffer.append(", ");
      }
    }

    buffer.append(") {").increaseDepth();

    buffer.writeLineBreak();

    // Lets the implementation write the body to the buffer.
    this.writeBodyToContext(buffer);

    buffer.decreaseDepth();
    buffer.writeLineBreak().append('}');
  }

  protected abstract void writeBodyToContext(final InternalGenerationContext buffer);

  static InternalMethodModel fromConsumer(
      final AccessModifier accessModifier,
      final String returnType,
      final String methodName,
      final Collection<InternalFieldModel> parameters,
      final Consumer<InternalGenerationContext> writingAction) {
    return new InternalMethodModel(accessModifier, returnType, methodName, parameters) {

      @Override
      protected void writeBodyToContext(InternalGenerationContext buffer) {
        writingAction.accept(buffer);
      }
    };
  }

  private static void handleConsoleInput(final String line) {
    parseIntFromString(line).orElseThrow(IllegalArgumentException::new);
  }

  private static OptionalInt parseIntFromString(final String source) {
    try {
      return OptionalInt.of(Integer.parseInt(source));
    } catch (final NumberFormatException parsingFailure) {
      return OptionalInt.empty();
    }
  }
}
