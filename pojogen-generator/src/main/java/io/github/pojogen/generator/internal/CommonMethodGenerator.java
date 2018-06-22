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
import java.util.function.Consumer;
import java.util.stream.Collectors;

final class CommonMethodGenerator {

  private static final String COMMON_METHOD_HASHCODE = "hashCode";
  private static final String COMMON_METHOD_EQUALS = "equals";
  private static final String COMMON_METHOD_EQUALS_PARAM = "OTHER";
  private static final String COMMON_METHOD_EQUALS_BODY =
      "if(this == {0}) {{2}return true;\n{3}}\nif({0} == null) {\n{2}return false;\n{3}}"
          + "\n\n{3}if (!({0} instanceof {1})) {\n{2}return false;\n{3}}\n\n{3}return {4};";

  private static final String COMMON_PRIMITIVES = "int|byte|short|long|float|double|char";

  MethodModel generateToStringMethod(final ClassModel container) {
    return null;
  }

  MethodModel generateEqualsMethod(final ClassModel container) {
    final VariableModel parameter =
        VariableModel.create(COMMON_METHOD_EQUALS_PARAM, Object.class.getSimpleName());

    final Collection<VariableModel> parameters = Collections.singleton(parameter);
    final Consumer<GenerationContext> bodyWriter =
        buffer -> {
          final StringBuilder fieldComparisons = new StringBuilder();
          buffer.writeFormatted(
              CommonMethodGenerator.COMMON_METHOD_EQUALS_BODY,
              CommonMethodGenerator.COMMON_METHOD_EQUALS_PARAM,
              container.getClassName(),
              buffer.increaseDepth().generateLinePrefix(),
              buffer.decreaseDepth().generateLinePrefix(),
              fieldComparisons.toString());
        };

    return MethodModel.fromConsumer(
        boolean.class.getSimpleName(),
        CommonMethodGenerator.COMMON_METHOD_EQUALS,
        parameters,
        AccessModifier.PUBLIC,
        bodyWriter);
  }

  MethodModel generateHashCodeMethod(final Collection<FieldModel> memberFields) {
    final Collection<String> arguments =
        memberFields.stream().map(field -> "this." + field.getName()).collect(Collectors.toList());

    final String body =
        MessageFormat.format(
            "return {0}.hash({1});", Objects.class.getName(), Joiner.on(", ").join(arguments));

    final Consumer<GenerationContext> bodyWriter = context -> context.writeFormatted(body);

    return MethodModel.fromConsumer(
        int.class.getSimpleName(),
        COMMON_METHOD_HASHCODE,
        Collections.emptyList(),
        AccessModifier.PUBLIC,
        bodyWriter);
  }

  private boolean isTypePrimitive(final String typeName) {
    return typeName.matches(COMMON_PRIMITIVES);
  }

  private String generateEqualsStatement(final String otherFieldName, final VariableModel model) {
    return this.isTypePrimitive(model.getTypeName())
        ? MessageFormat.format("this.{0} == {1}", model.getName(), otherFieldName)
        : MessageFormat.format("this.{0}.equals({1})", model.getName(), otherFieldName);
  }
}
