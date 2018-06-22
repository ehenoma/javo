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

import static java.util.Arrays.deepEquals;
import static java.util.Arrays.deepHashCode;
import static java.util.Arrays.deepToString;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import io.github.pojogen.generator.GenerationFlag;
import io.github.pojogen.struct.util.ObjectChecks;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class MethodModel implements GenerationStep {

  private static final AccessModifier FALLBACK_ACCESS_MODIFIER = AccessModifier.PACKAGE_PRIVATE;

  final AccessModifier accessModifier;
  final String returnType;
  final String methodName;
  final Collection<? extends VariableModel> parameters;

  protected MethodModel(
      final String methodName,
      final String returnType,
      final Collection<? extends VariableModel> parameters) {

    this(methodName, returnType, parameters, MethodModel.FALLBACK_ACCESS_MODIFIER);
  }

  protected MethodModel(
      final String methodName,
      final String returnType,
      final Collection<? extends VariableModel> parameters,
      final AccessModifier accessModifier) {

    this.accessModifier = accessModifier;
    this.returnType = returnType;
    this.methodName = methodName;
    this.parameters = ImmutableList.copyOf(parameters);
  }

  @Override
  public void writeToContext(final GenerationContext context) {
    context.write(this.accessModifier.getKeyword().orElse(""));
    context.write(" " + this.returnType);

    // The name might be null or empty (eg: Constructors)
    if (!Strings.isNullOrEmpty(this.methodName)) {
      context.write(" " + this.methodName);
    }

    context.write('(');
    this.writeParametersToContext(context);
    context.write(") {").increaseDepth().writeLineBreak();

    // Lets the implementation write the body to the context.
    this.writeBodyToContext(context);
    context.decreaseDepth().writeLineBreak().write('}').writeLineBreak();
  }

  private void writeParametersToContext(final GenerationContext context) {

    final Iterator<? extends VariableModel> parameterIterator = parameters.iterator();
    while (parameterIterator.hasNext()) {
      if (context.profile().hasFlag(GenerationFlag.LOCAL_VARIABLES_FINAL)) {
        context.write("final ");
      }

      final VariableModel parameter = parameterIterator.next();
      context.write(parameter.getTypeName()).write(' ').write(parameter.getName());

      if (parameterIterator.hasNext()) {
        context.write(", ");
      }
    }
  }

  protected abstract void writeBodyToContext(final GenerationContext buffer);

  @Override
  public int hashCode() {
    return Objects.hash(
        this.returnType,
        this.methodName,
        deepHashCode(this.parameters.toArray()),
        this.accessModifier.ordinal());
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", this.methodName)
        .add("returnType", this.returnType)
        .add("parameters", "{" + deepToString(this.parameters.toArray()) + "}")
        .add("accessModifier", this.accessModifier)
        .toString();
  }

  @Override
  public boolean equals(final Object checkTarget) {
    return ObjectChecks.equalsDefinitely(this, checkTarget)
        .orElseGet(() -> deepEquals(checkTarget));
  }

  private boolean deepEquals(final Object checkTarget) {
    final MethodModel otherMethod = (MethodModel) checkTarget;
    return otherMethod.methodName.equals(this.methodName)
        && otherMethod.returnType.equals(this.returnType)
        && accessModifier.equals(otherMethod.accessModifier)
        && Objects.deepEquals(this.parameters.toArray(), otherMethod.parameters.toArray());
  }

  static MethodModel fromConsumer(
      final String returnType,
      final String methodName,
      final Collection<? extends VariableModel> parameters,
      final AccessModifier accessModifier,
      final Consumer<GenerationContext> writingAction) {

    return new MethodModel(returnType, methodName, parameters, accessModifier) {

      @Override
      protected void writeBodyToContext(final GenerationContext buffer) {
        writingAction.accept(buffer);
      }
    };
  }
}
