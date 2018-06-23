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

import io.github.pojogen.generator.GenerationFlag;
import io.github.pojogen.generator.internal.GenerationContext;
import io.github.pojogen.generator.internal.model.AccessModifier;
import io.github.pojogen.generator.internal.model.MethodModel;
import io.github.pojogen.generator.internal.model.VariableModel;

import java.util.ArrayList;
import java.util.Collection;

public final class ToStringGenerator implements MethodGenerator {

  private static final MethodModel.Builder METHOD_TEMPLATE =
      MethodModel.newBuilder()
          .withMethodName("toString")
          .withReturnType("String")
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
    context.getBuffer().writeLine("return \"\";");
  }

  private void writeToContextInGuava(final GenerationContext context) {
    context.getBuffer().writeLine("return \"\";");
  }

  public Collection<VariableModel> getVariableModels() {
    return new ArrayList<>(this.variableModels);
  }
}
