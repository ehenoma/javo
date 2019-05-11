// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal.method;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.Preconditions;

import io.github.merlinosayimwen.javo.generator.internal.GenerationContext;
import io.github.merlinosayimwen.javo.generator.internal.model.AccessModifier;
import io.github.merlinosayimwen.javo.generator.internal.model.MethodModel;
import io.github.merlinosayimwen.javo.generator.internal.model.VariableModel;
import io.github.merlinosayimwen.javo.generator.internal.type.ObjectReferenceType;

import static java.text.MessageFormat.format;

public final class ConstructorGenerator implements MethodGenerator {

  private static final MethodModel.Builder TEMPLATE_METHOD =
      MethodModel.newBuilder().withAccessModifier(AccessModifier.PUBLIC);

  private final String typeName;
  private final Collection<VariableModel> attributes;

  private ConstructorGenerator(
      final String typeName, final Collection<? extends VariableModel> attributes) {

    this.typeName = typeName;
    this.attributes = new ArrayList<>(attributes);
  }

  @Override
  public MethodModel generate() {
    return ConstructorGenerator.TEMPLATE_METHOD
        .copy()
        .withReturnType(ObjectReferenceType.createConcrete(this.typeName))
        .withParameters(this.attributes)
        .withWriterAction(this::writeToContext)
        .create();
  }

  private void writeToContext(final GenerationContext context) {
    this.attributes.stream().map(this::formatterFunction).forEach(context.getBuffer()::writeLine);
  }

  private String formatterFunction(final VariableModel attribute) {
    final String copyStatement = attribute.getType().copyStatement(attribute.getName());
    return format("this.{0} = {1};", attribute.getName(), copyStatement);
  }

  public static ConstructorGenerator create(
      final String name, final Collection<? extends VariableModel> attributes) {

    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(attributes);
    return new ConstructorGenerator(name, attributes);
  }
}
