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

package io.github.pojogen.generator.internal.model;

import static java.text.MessageFormat.format;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import io.github.pojogen.generator.internal.GenerationContext;
import io.github.pojogen.generator.internal.GenerationStep;
import io.github.pojogen.struct.util.ObjectChecks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class MethodModel implements GenerationStep {

  private static final AccessModifier FALLBACK_ACCESS_MODIFIER = AccessModifier.PACKAGE_PRIVATE;

  private final AccessModifier accessModifier;
  private final String returnType;
  private final String methodName;
  private final Collection<String> annotations;
  private final Collection<VariableModel> parameters;
  private final Consumer<GenerationContext> contextWriterAction;

  private MethodModel(
      final String methodName,
      final String returnType,
      final Collection<String> annotations,
      final Collection<? extends VariableModel> parameters,
      final Consumer<GenerationContext> contextWriterAction,
      final AccessModifier accessModifier) {

    this.accessModifier = accessModifier;
    this.returnType = returnType;
    this.methodName = methodName;
    this.annotations = annotations;
    this.contextWriterAction = contextWriterAction;
    this.parameters = ImmutableList.copyOf(parameters);
  }

  @Override
  public void writeToContext(final GenerationContext context) {
    this.annotations.forEach(context.getBuffer()::writeLine);
    this.writeDeclarationBeginning(context);

    context.getBuffer().write("(");
    this.writeParametersToContext(context);

    context.getBuffer().write((") {"));
    context.getDepth().incrementByOne();
    context.getBuffer().writeLine();

    // Lets the action write the body to the context.
    this.contextWriterAction.accept(context);
    context.getDepth().decrementByOne();
    context.getBuffer().writeLine();
    context.getBuffer().writeLine("}");
  }

  private void writeDeclarationBeginning(final GenerationContext context) {
    final String methodDeclarationBeginning =
        this.accessModifier
            .getKeyword()
            .map(keyword -> keyword + ' ' + Strings.nullToEmpty(this.returnType))
            .orElse(Strings.nullToEmpty(this.returnType));

    context.getBuffer().write(methodDeclarationBeginning);

    // The name might be null or empty (eg: Constructors)
    if (!Strings.isNullOrEmpty(this.methodName)) {
      context.getBuffer().write(" " + this.methodName);
    }
  }

  private void writeParametersToContext(final GenerationContext context) {
    final Function<VariableModel, String> parameterToStringMapper =
        parameter -> format("{0} {1}", parameter.getTypeName(), parameter.getName());

    // TODO: Add line breaks when having a lot of parameters.
    final Collection<String> mappedParameters =
        this.parameters.stream().map(parameterToStringMapper).collect(Collectors.toList());

    final String stringParameters = Joiner.on(", ").join(mappedParameters);
    context.getBuffer().write(stringParameters);
  }

  public AccessModifier getAccessModifier() {
    return this.accessModifier;
  }

  public Stream<String> getAnnotations() {
    return this.annotations.stream();
  }

  public Stream<VariableModel> getParameters() {
    return this.parameters.stream();
  }

  public String getMethodName() {
    return this.methodName;
  }

  public String getReturnType() {
    return this.returnType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        this.returnType,
        this.methodName,
        this.accessModifier.ordinal(),
        Arrays.deepHashCode(this.annotations.toArray()),
        Arrays.deepHashCode(this.parameters.toArray()));
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", this.methodName)
        .add("returnType", this.returnType)
        .add("annotations", "{" + Joiner.on(", ").join(this.annotations) + "}")
        .add("parameters", "{" + Joiner.on(", ").join(this.parameters) + "}")
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
    return this.methodName.equals(otherMethod.methodName)
        && this.returnType.equals(otherMethod.returnType)
        && this.accessModifier.equals(otherMethod.accessModifier)
        && Arrays.deepEquals(this.annotations.toArray(), otherMethod.annotations.toArray())
        && Arrays.deepEquals(this.parameters.toArray(), otherMethod.parameters.toArray());
  }

  public static final class Builder {

    private AccessModifier accessModifier;
    private String returnType;
    private String methodName;
    private Collection<String> annotations;
    private Collection<VariableModel> parameters;
    private Consumer<GenerationContext> contextWriterAction;

    private Builder() {
      this.accessModifier = MethodModel.FALLBACK_ACCESS_MODIFIER;
      this.parameters = new ArrayList<>();
      this.annotations = new ArrayList<>();
    }

    public Builder withReturnType(final String returnType) {
      this.returnType = returnType;
      return this;
    }

    public Builder withMethodName(final String methodName) {
      this.methodName = methodName;
      return this;
    }

    public Builder withAccessModifier(final AccessModifier modifier) {
      this.accessModifier = modifier;
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
      this.ensureCollectionsPresent();
      this.parameters.add(parameter);
      return this;
    }

    private void ensureCollectionsPresent() {
      if (this.parameters == null) {
        this.parameters = new ArrayList<>();
      }

      if (this.annotations == null) {
        this.annotations = new ArrayList<>();
      }
    }

    public MethodModel create() {
      Preconditions.checkNotNull(this.contextWriterAction);
      Preconditions.checkNotNull(this.accessModifier);

      this.ensureCollectionsPresent();
      return new MethodModel(
          Strings.nullToEmpty(this.methodName),
          Strings.nullToEmpty(this.returnType),
          this.annotations,
          this.parameters,
          this.contextWriterAction,
          this.accessModifier);
    }
  }

  public static Builder newBuilder() {
    return new Builder();
  }
}
