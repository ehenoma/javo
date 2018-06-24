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

package io.github.pojogen.generator.internal.method;

import static java.text.MessageFormat.format;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import io.github.pojogen.generator.internal.GenerationContext;
import io.github.pojogen.generator.internal.model.AccessModifier;
import io.github.pojogen.generator.internal.model.MethodModel;
import io.github.pojogen.generator.internal.model.VariableModel;
import io.github.pojogen.generator.internal.type.PlainReferenceType;
import java.util.Collection;
import java.util.stream.Collectors;

public final class HashCodeGenerator implements MethodGenerator {

  private static final MethodModel.Builder TEMPLATE_METHOD =
      MethodModel.newBuilder()
          .withAccessModifier(AccessModifier.PUBLIC)
          .addAnnotation("@Override")
          .withReturnType(PlainReferenceType.createConcrete("int"))
          .withMethodName("hashCode");

  private final Collection<VariableModel> attributes;

  private HashCodeGenerator(final Collection<? extends VariableModel> attributes) {
    this.attributes = ImmutableList.copyOf(attributes);
  }

  public static HashCodeGenerator create(final Collection<? extends VariableModel> attributes) {
    Preconditions.checkNotNull(attributes);

    return new HashCodeGenerator(attributes);
  }

  @Override
  public MethodModel generate() {
    return HashCodeGenerator.TEMPLATE_METHOD.copy().withWriterAction(this::writeToContext).create();
  }

  private void writeToContext(final GenerationContext context) {
    if (this.attributes.isEmpty() {
      context.getBuffer().write("return 0;");
      return;
    }

    final Collection<String> arguments =
        attributes.stream().map(this::mapAttribute).collect(Collectors.toList());

    final String line = format("return Objects.hash({0});", Joiner.on(", ").join(arguments));
    context.getBuffer().write(line);
  }

  private String mapAttribute(final VariableModel attribute) {
    return attribute.getType().hashCodeStatement("this." + attribute.getName());
  }
}
