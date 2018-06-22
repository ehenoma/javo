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

import static java.util.Arrays.deepHashCode;
import static java.util.Arrays.deepToString;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import io.github.pojogen.generator.GenerationFlag;
import io.github.pojogen.struct.util.ObjectChecks;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public final class MethodModel implements GenerationStep {

  private static final AccessModifier FALLBACK_ACCESS_MODIFIER = AccessModifier.PACKAGE_PRIVATE;

  private final AccessModifier accessModifier;
  private final String returnType;
  private final String methodName;
  private final Collection<? extends VariableModel> parameters;
  private final Consumer<GenerationContext> contextWriterAction;

  private MethodModel(
      final String methodName,
      final String returnType,
      final Collection<? extends VariableModel> parameters,
      final Consumer<GenerationContext> contextWriterAction) {

    this(
        methodName,
        returnType,
        parameters,
        contextWriterAction,
        MethodModel.FALLBACK_ACCESS_MODIFIER);
  }

  private MethodModel(
      final String methodName,
      final String returnType,
      final Collection<? extends VariableModel> parameters,
      final Consumer<GenerationContext> contextWriterAction,
      final AccessModifier accessModifier) {

    this.accessModifier = accessModifier;
    this.returnType = returnType;
    this.methodName = methodName;
    this.contextWriterAction = contextWriterAction;
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

    // Lets the action write the body to the context.
    this.contextWriterAction.accept(context);
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

  AccessModifier getAccessModifier() {
    return this.accessModifier;
  }

  Collection<? extends VariableModel> getParameters() {
    return Lists.newArrayList(parameters);
  }

  String getMethodName() {
    return this.methodName;
  }

  String getReturnType() {
    return this.returnType;
  }

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
    if (!(checkTarget instanceof MethodModel)) {
      return false;
    }

    final MethodModel otherMethod = (MethodModel) checkTarget;
    return otherMethod.methodName.equals(this.methodName)
        && otherMethod.returnType.equals(this.returnType)
        && accessModifier.equals(otherMethod.accessModifier)
        && Objects.deepEquals(this.parameters.toArray(), otherMethod.parameters.toArray());
  }

  public static final class Builder {

    private AccessModifier accessModifier;
    private String returnType;
    private String methodName;
    private Collection<VariableModel> parameters;
    private Consumer<GenerationContext> contextWriterAction;

    private Builder() {
      this.accessModifier = MethodModel.FALLBACK_ACCESS_MODIFIER;
      this.parameters = new ArrayList<>();
    }

    public Builder withReturnType(final String returnType) {
      this.returnType = returnType;
      return this;
    }

    public Builder withMethodName(final String methodName) {
      this.methodName = methodName;
      return this;
    }

    public Builder withParameters(final Collection<? extends VariableModel> parameters) {
      this.parameters = new ArrayList<>(parameters);
      return this;
    }

    public Builder withWriterAction(final Consumer<GenerationContext> writerAction) {
      this.contextWriterAction = writerAction;
      return this;
    }

    public Builder addParameter(final VariableModel parameter) {
      this.ensureParameters();
      this.parameters.add(parameter);
      return this;
    }

    private void ensureParameters() {
      if (this.parameters == null) {
        this.parameters = new ArrayList<>();
      }
    }

    public MethodModel create() {
      Preconditions.checkNotNull(this.contextWriterAction);
      Preconditions.checkNotNull(this.accessModifier);

      this.ensureParameters();
      return new MethodModel(
          Strings.nullToEmpty(this.methodName),
          Strings.nullToEmpty(this.returnType),
          this.parameters,
          contextWriterAction,
          accessModifier);
    }
  }

  static Builder newBuilder() {
    return new Builder();
  }
}
