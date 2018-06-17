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

import com.google.common.base.Joiner;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

final class CommonMethodGenerator {

  private static final String COMMON_METHOD_HASHCODE = "hashCode";
  private static final String COMMON_METHOD_EQUALS = "equals";
  private static final String COMMON_METHOD_EQUALS_PARAM = "OTHER";
  public static final String COMMON_PRIMITIVES = "int|byte|short|long|float|double|char";

  MethodModel generateToStringMethod(final ClassModel container) {
    return null;
  }

  MethodModel generateEqualsMethod(final ClassModel container) {
    final Collection<FieldModel> parameters =
        Collections.singleton(
            new FieldModel(
                AccessModifier.PACKAGE_PRIVATE,
                CommonMethodGenerator.COMMON_METHOD_EQUALS_PARAM,
                Object.class.getSimpleName(),
                true));

    return MethodModel.fromConsumer(
        AccessModifier.PUBLIC,
        boolean.class.getSimpleName(),
        COMMON_METHOD_EQUALS,

        // Generates the methods body
        (buffer) -> {
          final StringBuilder fieldComparisons = new StringBuilder();

          buffer.writeFormatted(
              "if({0} == null) {"
                  + "\n{2}return false;"
                  + "\n{3}}"
                  + "\n\n{3}if (!({0} instanceof {1})) {"
                  + "\n{2}return false;"
                  + "\n{3}}"
                  + "\n\n{3}return {4};",
              COMMON_METHOD_EQUALS_PARAM,
              container.getClassName(),
              buffer.increaseDepth().generateLinePrefix(),
              buffer.decreaseDepth().generateLinePrefix(),
              fieldComparisons.toString());
        });
  }

  MethodModel generateHashCodeMethod(final Collection<FieldModel> memberFields) {
    return MethodModel.fromConsumer(
        AccessModifier.PUBLIC,
        int.class.getSimpleName(),
        COMMON_METHOD_HASHCODE,
        Collections.emptyList(),

        // Lambda expression that creates an consumer which will write the body to the buffer.
        // Simply uses the {Objects#hash(...)} method to generate a hashcode.
        (buffer) -> {
          buffer
              .append("return ")
              .append(Objects.class.getName())
              .append(".hash(")
              .append(
                  // Joins the fields of the class to one string.
                  // Also uses maps to append the "this." prefix to the fieldName.
                  Joiner.on(", ")
                      .join(
                          memberFields
                              .stream()
                              .map((field) -> "this." + field.getName())
                              .collect(Collectors.toList())))
              .append(");");
        });
  }

  private boolean isTypePrimitive(final String typeName) {
    return typeName.matches(COMMON_PRIMITIVES);
  }

  private String generateEqualsStatement(
      final String otherFieldName, final FieldModel field) {
    return this.isTypePrimitive(field.getTypeName())
        ? MessageFormat.format("this.{0} == {1}", field.getName(), otherFieldName)
        : MessageFormat.format("this.{0}.equals({1})", field.getName(), otherFieldName);
  }
}
