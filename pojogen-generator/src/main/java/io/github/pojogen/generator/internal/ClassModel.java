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

package io.github.pojogen.generator.internal;

import com.google.common.collect.ImmutableList;
import java.util.Collection;

final class ClassModel implements GenerationStep {

  private final String className;
  private final Collection<FieldModel> fields;
  private final Collection<ConstructorModel> constructors;
  private final Collection<MethodModel> methods;

  public ClassModel(
      final String className,
      final Collection<FieldModel> fields,
      final Collection<ConstructorModel> constructors,
      final Collection<MethodModel> methods) {

    this.className = className;
    this.fields = ImmutableList.copyOf(fields);
    this.constructors = ImmutableList.copyOf(constructors);
    this.methods = ImmutableList.copyOf(methods);
  }

  @Override
  public void writeToContext(final GenerationContext context) {
    context.writeFormatted("public final class {0} '{'", this.className);
    context.increaseDepth().writeLineBreak();

    fields.forEach(fieldModel -> fieldModel.writeToContext(context));
    constructors.forEach(constructorModel -> constructorModel.writeToContext(context));
    methods.forEach(methodModel -> methodModel.writeToContext(context));

    context.decreaseDepth().writeLineBreak();
    context.writeFormatted("'}'");
  }

  public String getClassName() {
    return this.className;
  }

  public Collection<FieldModel> getFields() {
    return this.fields;
  }

  public Collection<ConstructorModel> getConstructors() {
    return this.constructors;
  }

  public Collection<MethodModel> getMethods() {
    return this.methods;
  }
}
