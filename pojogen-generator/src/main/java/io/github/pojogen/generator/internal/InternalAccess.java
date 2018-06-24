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

import com.google.common.base.Suppliers;
import io.github.pojogen.generator.PojoGenerator;
import io.github.pojogen.generator.PojoGeneratorFactory;
import java.util.function.Supplier;

public final class InternalAccess {

  private InternalAccess() {}

  public static Supplier<PojoGeneratorFactory> getInternalGeneratorFactorySupply() {
    return Suppliers.memoize(InternalPojoGeneratorFactory::new);
  }

  static final class InternalPojoGeneratorFactory extends PojoGeneratorFactory {

    @Override
    public PojoGenerator getInstance() {
      return new InternalPojoGenerator();
    }
  }
}
