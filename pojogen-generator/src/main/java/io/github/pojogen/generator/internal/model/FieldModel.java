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

package io.github.pojogen.generator.internal.model;

import com.google.common.base.Preconditions;

import io.github.pojogen.generator.internal.GenerationContext;
import io.github.pojogen.generator.internal.GenerationStep;
import io.github.pojogen.struct.StructAttribute;

public final class FieldModel extends VariableModel implements GenerationStep {

  private final AccessModifier accessModifier;

  private FieldModel(
      final String name,
      final String typeName,
      final boolean modifiable,
      final AccessModifier accessModifier) {

    super(name, typeName, modifiable);
    this.accessModifier = accessModifier;
  }

  @Override
  public final void writeToContext(final GenerationContext context) {
    this.accessModifier.getKeyword().ifPresent(keyword -> context.getBuffer().write(keyword + " "));
    super.writeToContext(context);
    context.getBuffer().write(";");
  }

  public AccessModifier getAccessModifier() {
    return this.accessModifier;
  }

  static FieldModel fromVariable(
      final VariableModel variable, final AccessModifier accessModifier) {

    Preconditions.checkNotNull(variable);
    Preconditions.checkNotNull(accessModifier);
    return new FieldModel(
        variable.getName(), variable.getTypeName(), variable.isModifiable(), accessModifier);
  }

  static FieldModel fromStructAttribute(final StructAttribute attribute) {
    Preconditions.checkNotNull(attribute);

    return fromVariable(
        VariableModel.create(attribute.getName(), attribute.getTypeName(), !attribute.isConstant()),
        AccessModifier.PRIVATE);
  }
}
