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

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import io.github.pojogen.generator.internal.GenerationContext;
import io.github.pojogen.generator.internal.model.AccessModifier;
import io.github.pojogen.generator.internal.model.MethodModel;
import io.github.pojogen.generator.internal.model.VariableModel;
import io.github.pojogen.generator.internal.type.ObjectReferenceType;
import java.util.Collection;

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
