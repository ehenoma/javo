// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal.method;

import com.google.common.base.Preconditions;

import io.github.merlinosayimwen.javo.generator.internal.GenerationContext;
import io.github.merlinosayimwen.javo.generator.internal.model.AccessModifier;
import io.github.merlinosayimwen.javo.generator.internal.model.MethodModel;
import io.github.merlinosayimwen.javo.generator.internal.model.VariableModel;
import io.github.merlinosayimwen.javo.util.naming.NamingConvention;
import io.github.merlinosayimwen.javo.util.naming.UpperCamelCaseNamingConvention;

import static java.text.MessageFormat.format;

public final class SetterGenerator implements MethodGenerator {

  private static final NamingConvention NAMING_CONVENTION = new UpperCamelCaseNamingConvention();

  private static final MethodModel.Builder TEMPLATE_METHOD =
      MethodModel.newBuilder().withAccessModifier(AccessModifier.PUBLIC);

  private final VariableModel attribute;

  private SetterGenerator(final VariableModel variableModel) {
    this.attribute = variableModel;
  }

  @Override
  public MethodModel generate() {
    final String methodName =
        format("set{0}", SetterGenerator.NAMING_CONVENTION.apply(this.attribute.getName()));

    return SetterGenerator.TEMPLATE_METHOD
        .copy()
        .withMethodName(methodName)
        .addParameter(this.attribute)
        .withWriterAction(this::writeToContext)
        .create();
  }

  private void writeToContext(final GenerationContext context) {
    final String copyStatement = this.attribute.getType().copyStatement(this.attribute.getName());
    context.getBuffer().write(format("this.{0} = {1};", this.attribute.getName(), copyStatement));
  }

  public static SetterGenerator create(final VariableModel variableModel) {
    Preconditions.checkNotNull(variableModel);

    return new SetterGenerator(variableModel);
  }
}
