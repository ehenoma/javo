// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal;

import com.google.common.base.Suppliers;
import io.github.merlinosayimwen.javo.generator.JavoGenerator;
import io.github.merlinosayimwen.javo.generator.JavoGeneratorFactory;
import java.util.function.Supplier;

public final class InternalAccess {

  private InternalAccess() {}

  public static Supplier<JavoGeneratorFactory> getInternalGeneratorFactorySupply() {
    return Suppliers.memoize(InternalPojoGeneratorFactory::new);
  }

  static final class InternalPojoGeneratorFactory implements JavoGeneratorFactory {

    @Override
    public JavoGenerator getInstance() {
      return new InternalPojoGenerator();
    }
  }
}
