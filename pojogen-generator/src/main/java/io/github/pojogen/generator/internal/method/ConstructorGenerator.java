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
import io.github.pojogen.generator.internal.type.ObjectReferenceType;
import java.util.ArrayList;
import java.util.Collection;

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
