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

import com.google.common.base.Preconditions;
import java.util.Collection;

final class ConstructorModel extends MethodModel {

  ConstructorModel(
      final String className,
      final Collection<FieldModel> parameters,
      final AccessModifier accessModifier) {

    super(className, "", parameters, accessModifier);
  }

  @Override
  protected void writeBodyToContext(final GenerationContext buffer) {
    for (final VariableModel parameter : parameters) {
      // TODO: Check whether type is of array or collection and should be shallow copied.
      buffer.writeFormatted("this.{0} = {0}", parameter.getName());
      buffer.writeLineBreak();
    }
  }

  static ConstructorModel create(
      final String className,
      final Collection<FieldModel> parameters,
      final AccessModifier accessModifier) {

    Preconditions.checkNotNull(className);
    Preconditions.checkNotNull(parameters);
    Preconditions.checkNotNull(accessModifier);
    return new ConstructorModel(className, parameters, accessModifier);
  }
}
