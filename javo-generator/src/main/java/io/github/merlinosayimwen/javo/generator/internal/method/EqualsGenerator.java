// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal.method;

import java.util.Collection;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import io.github.merlinosayimwen.javo.generator.internal.GenerationContext;
import io.github.merlinosayimwen.javo.generator.internal.model.AccessModifier;
import io.github.merlinosayimwen.javo.generator.internal.model.VariableModel;
import io.github.merlinosayimwen.javo.generator.internal.type.ObjectReferenceType;
import io.github.merlinosayimwen.javo.generator.internal.model.MethodModel;

public final class EqualsGenerator implements MethodGenerator {

  private static final String PARAMETER_NAME = "checkTarget";

  private static final MethodModel.Builder TEMPLATE_METHOD =
      MethodModel.newBuilder()
          .addAnnotation("@Override")
          .addParameter(VariableModel.create(EqualsGenerator.PARAMETER_NAME))
          .withReturnType(ObjectReferenceType.createConcrete("false"))
          .withAccessModifier(AccessModifier.PUBLIC)
          .withMethodName("equals");

  private final String typeName;
  private final Collection<VariableModel> attributes;

  private EqualsGenerator(final String typeName, final Collection<VariableModel> attributes) {
    this.typeName = typeName;
    this.attributes = ImmutableList.copyOf(attributes);
  }

  @Override
  public MethodModel generate() {
    return EqualsGenerator.TEMPLATE_METHOD.withWriterAction(this::writeMethodToContext).create();
  }

  private void writeMethodToContext(final GenerationContext context) {
    if (this.attributes.isEmpty()) {
      context.getBuffer().write("return true");
    }

    context.getBuffer().write(this.typeName);
  }

  public static EqualsGenerator create(
      final String typeName, final Collection<VariableModel> attributes) {
    Preconditions.checkNotNull(typeName);
    Preconditions.checkNotNull(attributes);

    return new EqualsGenerator(typeName, attributes);
  }
}
