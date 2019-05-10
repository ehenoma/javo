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

package io.github.pojogen.generator.internal.type;

import static java.text.MessageFormat.format;

import com.google.common.base.Preconditions;

public final class CollectionReferenceType extends ReferenceType {

  private CollectionReferenceType(final String typeName, final boolean concrete) {
    super(typeName, true, concrete, false);
  }

  @Override
  public String copyStatement(String variableName) {
    return null;
  }

  @Override
  public String equalsStatement(String variableName, String otherVariable) {
    return format("Arrays.deepEquals({0}.toArray(),{1}.toArray())", variableName, otherVariable);
  }

  @Override
  public String hashCodeStatement(String variableName) {
    return format("Arrays.deepHashCode({0}.toArray())", variableName);
  }

  @Override
  public String toStringStatement(String variableName) {
    return format("Joiner.on(\", \").join({0})", variableName);
  }

  public static CollectionReferenceType createConcrete(final String typeName) {
    return create(typeName, false);
  }

  public static CollectionReferenceType createAbstract(final String typeName) {
    return create(typeName, false);
  }

  public static CollectionReferenceType create(final String typeName, final boolean concrete) {
    Preconditions.checkNotNull(typeName);

    return new CollectionReferenceType(typeName, concrete);
  }
}
