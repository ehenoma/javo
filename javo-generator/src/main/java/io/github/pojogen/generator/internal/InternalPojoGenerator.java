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
import io.github.pojogen.generator.GenerationProfile;
import io.github.pojogen.generator.PojoGenerator;
import io.github.pojogen.generator.internal.method.ConstructorGenerator;
import io.github.pojogen.generator.internal.method.GetterGenerator;
import io.github.pojogen.generator.internal.method.SetterGenerator;
import io.github.pojogen.generator.internal.model.ClassModel;
import io.github.pojogen.generator.internal.model.FieldModel;
import io.github.merlinosayimwen.pojogen.struct.Struct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

final class InternalPojoGenerator implements PojoGenerator {

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
