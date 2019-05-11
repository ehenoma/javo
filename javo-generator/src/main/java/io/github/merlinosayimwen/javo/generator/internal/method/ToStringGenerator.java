// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal.method;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.Preconditions;

import io.github.merlinosayimwen.javo.generator.GenerationFlag;
import io.github.merlinosayimwen.javo.generator.internal.GenerationContext;
import io.github.merlinosayimwen.javo.generator.internal.model.AccessModifier;
import io.github.merlinosayimwen.javo.generator.internal.model.MethodModel;
import io.github.merlinosayimwen.javo.generator.internal.model.VariableModel;
import io.github.merlinosayimwen.javo.generator.internal.type.ObjectReferenceType;

import static java.text.MessageFormat.format;

public final class ToStringGenerator implements MethodGenerator {

  private static final MethodModel.Builder METHOD_TEMPLATE =
      MethodModel.newBuilder()
          .withMethodName("toString")
          .withReturnType(ObjectReferenceType.createConcrete("String"))
          .addAnnotation("@Override")
          .withAccessModifier(AccessModifier.PUBLIC);

  private final Collection<VariableModel> variableModels;

  private ToStringGenerator(final Collection<? extends VariableModel> variableModels) {
    this.variableModels = new ArrayList<>(variableModels);
  }

  @Override
  public MethodModel generate() {
    return ToStringGenerator.METHOD_TEMPLATE.copy().withWriterAction(this::writeToContext).create();
  }

  private void writeToContext(final GenerationContext context) {
    if (context.getProfile().hasFlag(GenerationFlag.DEPENDENCY_GUAVA)) {
      this.writeToContextInGuava(context);
      return;
    }

    this.writeToContextInPlainJava(context);
  }

  private void writeToContextInPlainJava(final GenerationContext context) {
    final String formattedStatement =
        format("return String.format(\"%s({0})\", this.getClass().getSimpleName(), {1});", "", "");

    context.getBuffer().write(formattedStatement);
  }

  private void writeToContextInGuava(final GenerationContext context) {
    context.getBuffer().writeLine("return MoreObjects.toStringHelper(this)");
    for (final VariableModel variable : this.variableModels) {
      final String toStringStatement =
          variable.getType().toStringStatement("this." + variable.getName());

      final String line = format("\t.add(\"{0}\", {1})", variable.getName(), toStringStatement);
      context.getBuffer().writeLine(line);
    }
    context.getBuffer().write("\t.toString();");
  }

  public Collection<VariableModel> getVariableModels() {
    return new ArrayList<>(this.variableModels);
  }

  public static ToStringGenerator create(final Collection<? extends VariableModel> variableModels) {
    Preconditions.checkNotNull(variableModels);

    return new ToStringGenerator(variableModels);
  }
}
