// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal.model;

import static java.text.MessageFormat.format;

import com.google.common.base.Preconditions;
import io.github.merlinosayimwen.javo.generator.internal.GenerationContext;
import io.github.merlinosayimwen.javo.generator.internal.GenerationStep;
import io.github.merlinosayimwen.javo.generator.internal.method.HashCodeGenerator;
import io.github.merlinosayimwen.javo.generator.internal.method.MethodGenerator;
import io.github.merlinosayimwen.javo.generator.internal.method.ToStringGenerator;
import io.github.merlinosayimwen.javo.generator.internal.method.EqualsGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ClassModel implements GenerationStep {

  private final String className;
  private final Collection<GenerationStep> members;

  private ClassModel(final String className, final Collection<? extends GenerationStep> members) {
    this.className = className;
    this.members = new ArrayList<>(members);
  }

  @Override
  public void writeToContext(final GenerationContext context) {
    final Consumer<GenerationStep> writeStepToContextFunction =
        step -> step.writeToContext(context);

    context.getBuffer().write(format("public final class {0} '{'", this.className));
    context.getDepth().incrementByOne();
    context.getBuffer().writeLine();
    context.getBuffer().writeLine();

    // Writes every member to the context.
    this.members.forEach(writeStepToContextFunction);

    context.getDepth().decrementByOne();
    context.getBuffer().writeLine();
    context.getBuffer().write("}");
  }

  private void generateCommonMethods() {
    final Collection<VariableModel> attributes =
        this.members
            .stream()
            .filter(member -> member instanceof VariableModel)
            .map(member -> (VariableModel) member)
            .collect(Collectors.toList());

    final MethodGenerator toString = ToStringGenerator.create(attributes);
    final MethodGenerator hashCode = HashCodeGenerator.create(attributes);
    final MethodGenerator equals = EqualsGenerator.create(this.className, attributes);
    this.members.add(equals.generate());
    this.members.add(toString.generate());
    this.members.add(hashCode.generate());
  }

  public String getClassName() {
    return this.className;
  }

  public Stream<GenerationStep> getMembers() {
    return this.members.stream();
  }

  public static ClassModel create(
      final String className, final Collection<? extends GenerationStep> members) {
    Preconditions.checkNotNull(className);
    Preconditions.checkNotNull(members);

    final ClassModel model = new ClassModel(className, members);
    model.generateCommonMethods();

    return model;
  }
}
