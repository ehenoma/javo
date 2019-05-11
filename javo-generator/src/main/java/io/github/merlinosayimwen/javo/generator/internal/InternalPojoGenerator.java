// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal;


import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import io.github.merlinosayimwen.javo.Struct;
import io.github.merlinosayimwen.javo.generator.internal.method.ConstructorGenerator;
import io.github.merlinosayimwen.javo.generator.internal.method.GetterGenerator;
import io.github.merlinosayimwen.javo.generator.internal.method.SetterGenerator;
import io.github.merlinosayimwen.javo.generator.GenerationProfile;
import io.github.merlinosayimwen.javo.generator.JavoGenerator;
import io.github.merlinosayimwen.javo.generator.internal.model.ClassModel;
import io.github.merlinosayimwen.javo.generator.internal.model.FieldModel;

final class InternalPojoGenerator implements JavoGenerator {

  @Override
  public String generate(final Struct model) {
    return this.generate(model, GenerationProfile.create());
  }

  @Override
  public String generate(final Struct model, final GenerationProfile profile) {
    Preconditions.checkNotNull(model);
    Preconditions.checkNotNull(profile);

    final GenerationContext context = GenerationContext.create(profile);
    final ClassModel parentModel = ClassModel.create(model.getName(), this.fillSteps(model));

    parentModel.writeToContext(context);
    return context.finish();
  }

  private Collection<GenerationStep> fillSteps(final Struct model) {
    final Collection<FieldModel> fields =
        model.getAttributes().map(FieldModel::fromStructAttribute).collect(Collectors.toList());

    final Collection<GenerationStep> steps = new ArrayList<>(fields);
    steps.add(new LineBreakGenerationStep());
    steps.add(ConstructorGenerator.create(model.getName(), fields).generate());
    fields
        .stream()
        .filter(FieldModel::isModifiable)
        .map(SetterGenerator::create)
        .map(SetterGenerator::generate)
        .forEach(steps::add);

    fields.stream().map(GetterGenerator::create).map(GetterGenerator::generate).forEach(steps::add);
    return steps;
  }
}
