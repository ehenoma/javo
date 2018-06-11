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

final class InternalClassModel implements InternalGenerationStep {

  private final String className;
  private final Collection<InternalFieldModel> fields;
  private final Collection<InternalConstructorModel> constructors;
  private final Collection<InternalMethodModel> methods;

  public InternalClassModel(
      final String className,
      final Collection<InternalFieldModel> fields,
      final Collection<InternalConstructorModel> constructors,
      final Collection<InternalMethodModel> methods) {
    this.className = className;
    this.fields = ImmutableList.copyOf(fields);
    this.constructors = ImmutableList.copyOf(constructors);
    this.methods = ImmutableList.copyOf(methods);
  }

  @Override
  public void writeToContext(final InternalGenerationContext context) {}

  public String getClassName() {
    return this.className;
  }

  public Collection<InternalFieldModel> getFields() {
    return this.fields;
  }

  public Collection<InternalConstructorModel> getConstructors() {
    return this.constructors;
  }

  public Collection<InternalMethodModel> getMethods() {
    return this.methods;
  }
}
