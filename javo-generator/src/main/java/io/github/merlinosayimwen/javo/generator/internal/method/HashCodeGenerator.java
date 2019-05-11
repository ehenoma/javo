// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal.method;

import java.util.Collection;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import io.github.merlinosayimwen.javo.generator.internal.GenerationContext;
import io.github.merlinosayimwen.javo.generator.internal.model.AccessModifier;
import io.github.merlinosayimwen.javo.generator.internal.model.MethodModel;
import io.github.merlinosayimwen.javo.generator.internal.model.VariableModel;
import io.github.merlinosayimwen.javo.generator.internal.type.ObjectReferenceType;

import static java.text.MessageFormat.format;

public final class HashCodeGenerator implements MethodGenerator {

  private static final MethodModel.Builder TEMPLATE_METHOD =
      MethodModel.newBuilder()
          .withAccessModifier(AccessModifier.PUBLIC)
          .addAnnotation("@Override")
          .withReturnType(ObjectReferenceType.createConcrete("int"))
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
    if (this.attributes.isEmpty()) {
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
