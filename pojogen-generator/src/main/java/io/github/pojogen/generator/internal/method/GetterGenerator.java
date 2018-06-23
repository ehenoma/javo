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

public class GetterGenerator implements MethodGenerator {

  private static final NamingConvention NAMING_CONVENTION = new UpperCamelCaseNamingConvention();
  private static final MethodModel.Builder TEMPLATE_MODEL = MethodModel.newBuilder();

  private final VariableModel attribute;

  private GetterGenerator(final VariableModel attribute) {
    this.attribute = attribute;
  }

  @Override
  public MethodModel generate() {
    return GetterGenerator.TEMPLATE_MODEL
        .copy()
        .withReturnType(this.attribute.getTypeName())
        .withAccessModifier(AccessModifier.PUBLIC)
        .withMethodName(
            format("get{0}", GetterGenerator.NAMING_CONVENTION.apply(this.attribute.getName())))
        .withWriterAction(this::writeToContext)
        .create();
  }

  // TODO: Shallow copy for mutable classes.
  private void writeToContext(final GenerationContext context) {
    context.getBuffer().write(format("return this.{0};", this.attribute.getName()));
  }

  public static GetterGenerator create(final VariableModel attribute) {
    Preconditions.checkNotNull(attribute);

    return new GetterGenerator(attribute);
  }
}
