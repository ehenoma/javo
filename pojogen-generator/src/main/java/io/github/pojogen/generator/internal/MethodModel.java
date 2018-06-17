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

public abstract class MethodModel {

  protected final String returnType;
  protected final String methodName;
  protected final Collection<FieldModel> parameters;

  private MethodModel(
      final String methodName,
      final String returnType,
      final Collection<FieldModel> parameters) {}

  private MethodModel(
      final String methodName,
      final String returnType,
      final Collection<FieldModel> parameters,
      final AccessModifier InternalAccessModifier) {

    super(accessModifier);
    this.returnType = returnType;
    this.methodName = methodName;
    this.parameters = ImmutableList.copyOf(parameters);
  }

  @Override
  public void writeToContext(GenerationContext context) {
    context.write(this.accessModifier.getCodeRepresentation()).append(' ').append(this.returnType);

    if (!Strings.isNullOrEmpty(this.methodName)) {
      context.write(' ').write(this.methodName);
    }

    context.write('(');

    final Iterator<FieldModel> parameterIterator = parameters.iterator();
    while (parameterIterator.hasNext()) {
      if (context.profile().hasFlag(GenerationFlag.LOCAL_VARIABLES_FINAL)) {
        context.write("final ");
      }

      final FieldModel parameter = parameterIterator.next();
      context.write(parameter.getTypeName()).write(' ').append(parameter.getName());

      if (parameterIterator.hasNext()) {
        context.write(", ");
      }
    }

    context.write(") {").increaseDepth();

    context.writeLineBreak();

    // Lets the implementation write the body to the context.
    this.writeBodyToContext(context);

    context.decreaseDepth();
    context.writeLineBreak().write('}');
  }

  protected abstract void writeBodyToContext(final GenerationContext buffer);

  static MethodModel fromConsumer(
      final AccessModifier InternalAccessModifier,
      final String returnType,
      final String methodName,
      final Collection<FieldModel> parameters,
      final Consumer<GenerationContext> writingAction) {
    return new MethodModel(accessModifier, returnType, methodName, parameters) {

      @Override
      protected void writeBodyToContext(GenerationContext buffer) {
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
