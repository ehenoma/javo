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
import com.google.common.collect.Maps;
import io.github.pojogen.generator.util.naming.NamingConvention;
import io.github.pojogen.generator.util.naming.UpperCamelCaseNamingConvention;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ObjectReferenceType extends ReferenceType {

  private static final String GENERIC_MATCHER_EXPRESSION = "\\w+\\s*<(?:\\w+)?>";

  private static final Collection<String> PRIMITIVE_TYPES =
      Arrays.asList("byte", "short", "int", "long", "float", "double", "char", "boolean");

  private static final Map<String, String> WRAPPER_CLASSES;

  static {
    final NamingConvention convention = new UpperCamelCaseNamingConvention();

    WRAPPER_CLASSES = new HashMap<>(Maps.toMap(PRIMITIVE_TYPES, convention::apply));
    WRAPPER_CLASSES.put("char", "Character");
    WRAPPER_CLASSES.put("int", "Integer");
  }

  private ObjectReferenceType(
      final String typeName,
      final boolean generic,
      final boolean concrete,
      final boolean primitive) {

    super(typeName, generic, concrete, primitive);
  }

  @Override
  public String toStringStatement(String variableName) {
    return this.isPrimitive()
        ? this.primitiveStringStatement(variableName)
        : this.objectStringStatement(variableName);
  }

  private String objectStringStatement(final String variableName) {
    return variableName + ".toString()";
  }

  private String primitiveStringStatement(final String variableName) {
    return format("String.valueOf({0})", variableName);
  }

  @Override
  public String hashCodeStatement(final String variableName) {
    return this.isPrimitive()
        ? this.primitiveHashCodeStatement(variableName)
        : this.objectHashCodeStatement(variableName);
  }

  private String objectHashCodeStatement(final String variableName) {
    return variableName + ".hashCode()";
  }

  private String primitiveHashCodeStatement(final String variableName) {
    final String className =
        ObjectReferenceType.WRAPPER_CLASSES.getOrDefault(getTypeName(), "Objects");

    return format("{0}.hashCode({1})", className, variableName);
  }

  @Override
  public String equalsStatement(final String variableName, final String otherVariable) {
    return this.isPrimitive()
        ? this.primitiveEqualsStatement(variableName, otherVariable)
        : this.objectEqualsStatement(variableName, otherVariable);
  }

  private String objectEqualsStatement(final String variableName, final String otherVariableName) {
    return format("{0}.equals({1})", variableName, otherVariableName);
  }

  private String primitiveEqualsStatement(
      final String variableName, final String otherVariableName) {

    return format("{0} == {1}", variableName, otherVariableName);
  }

  @Override
  public String copyStatement(final String variableName) {
    // Simply doesn't copy the reference.
    return variableName;
  }

  public static ObjectReferenceType createConcrete(final String typeName) {
    return ObjectReferenceType.create(typeName, true);
  }

  public static ObjectReferenceType create(final String typeName, final boolean concrete) {
    Preconditions.checkNotNull(typeName);

    final boolean primitive = ObjectReferenceType.PRIMITIVE_TYPES.contains(typeName);
    final boolean generic =
        !primitive && typeName.matches(ObjectReferenceType.GENERIC_MATCHER_EXPRESSION);

    return new ObjectReferenceType(typeName, generic, concrete, primitive);
  }
}
