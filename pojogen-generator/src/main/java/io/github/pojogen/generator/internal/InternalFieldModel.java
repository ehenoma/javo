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

/**
 * @author Merlin Osayimwen
 * @see InternalVariableModel
 * @see InternalGenerationStep
 * @since 1.0
 */
final class InternalFieldModel extends InternalVariableModel implements InternalGenerationStep {

  private static final InternalAccessModifier DEFAULT_ACCESSMODIFIER =
      InternalAccessModifier.PACKAGE_PRIVATE;

  private final InternalAccessModifier accessModifier;

  private InternalFieldModel(final String name) {
    this(name, InternalVariableModel.DEFAULT_TYPENAME);
  }

  private InternalFieldModel(final String name, final String typeName) {
    this(name, typeName, InternalVariableModel.DEFAULT_MODIFIABLE);
  }

  private InternalFieldModel(final String name, final String typeName, final boolean modifiable) {
    this(name, typeName, modifiable, InternalFieldModel.DEFAULT_ACCESSMODIFIER);
  }

  private InternalFieldModel(
      final String name,
      final String typeName,
      final boolean modifiable,
      final InternalAccessModifier accessModifier) {
    super(name, typeName, modifiable);
    this.accessModifier = accessModifier;
  }

  @Override
  public final void writeToContext(final InternalGenerationContext context) {
    context.write(this.accessModifier.getKeyword()).write(' ');

    super.writeToContext(context);
    context.write(';');
  }

  public InternalAccessModifier getAccessModifier() {
    return this.accessModifier;
  }
}
