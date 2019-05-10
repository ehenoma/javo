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

package io.github.pojogen.generator.internal.method;

import static java.text.MessageFormat.format;

import com.google.common.base.Preconditions;
import io.github.pojogen.generator.internal.GenerationContext;
import io.github.pojogen.generator.internal.model.AccessModifier;
import io.github.pojogen.generator.internal.model.MethodModel;
import io.github.pojogen.generator.internal.model.VariableModel;
import io.github.pojogen.generator.util.naming.NamingConvention;
import io.github.pojogen.generator.util.naming.UpperCamelCaseNamingConvention;

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
