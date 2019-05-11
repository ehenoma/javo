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

public class GetterGenerator implements MethodGenerator {

  private static final NamingConvention NAMING_CONVENTION = new UpperCamelCaseNamingConvention();
  private static final MethodModel.Builder TEMPLATE_MODEL = MethodModel.newBuilder();

  private final VariableModel attribute;

  private GetterGenerator(final VariableModel attribute) {
    this.attribute = attribute;
  }

  @Override
  public MethodModel generate() {
    final String methodName =
        format("get{0}", GetterGenerator.NAMING_CONVENTION.apply(this.attribute.getName()));

    return GetterGenerator.TEMPLATE_MODEL
        .copy()
        .withReturnType(this.attribute.getType())
        .withAccessModifier(AccessModifier.PUBLIC)
        .withMethodName(methodName)
        .withWriterAction(this::writeToContext)
        .create();
  }

  private void writeToContext(final GenerationContext context) {
    final String copyStatement = this.attribute.getType().copyStatement(this.attribute.getName());

    context.getBuffer().write(format("return this.{0};", copyStatement));
  }

  public static GetterGenerator create(final VariableModel attribute) {
    Preconditions.checkNotNull(attribute);

    return new GetterGenerator(attribute);
  }
}
