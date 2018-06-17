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
import io.github.pojogen.struct.Struct;
import java.util.Collection;
import java.util.Collections;
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

    try (final GenerationContext context = GenerationContext.create(profile, "  ")) {
      final Collection<FieldModel> fieldMembers =
          model.getAttributes().map(FieldModel::fromStructAttribute).collect(Collectors.toList());

      final ClassModel parentModel =
          new ClassModel(
              model.getName(), fieldMembers, Collections.emptyList(), Collections.emptyList());

      parentModel.writeToContext(context);
      return context.produceResult();
    }
  }
}
