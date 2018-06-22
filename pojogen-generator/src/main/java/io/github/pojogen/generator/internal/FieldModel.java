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

import static io.github.pojogen.generator.internal.AccessModifier.PACKAGE_PRIVATE;

import com.google.common.base.Preconditions;
import io.github.pojogen.struct.StructAttribute;

final class FieldModel extends VariableModel implements GenerationStep {

  private static final AccessModifier DEFAULT_ACCESS_MODIFIER = PACKAGE_PRIVATE;

  private final AccessModifier accessModifier;

  private FieldModel(final String name) {
    this(name, VariableModel.DEFAULT_TYPENAME);
  }

  private FieldModel(final String name, final String typeName) {
    this(name, typeName, VariableModel.DEFAULT_MODIFIABLE);
  }

  private FieldModel(final String name, final String typeName, final boolean modifiable) {
    this(name, typeName, modifiable, FieldModel.DEFAULT_ACCESS_MODIFIER);
  }

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
    context.write(this.accessModifier.getKeyword().orElse("")).write(' ');

    super.writeToContext(context);
    context.write(";").writeLineBreak();
  }

  AccessModifier getAccessModifier() {
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
