// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator;

import io.github.merlinosayimwen.javo.generator.internal.InternalAccess;

public interface JavoGeneratorFactory {

  JavoGenerator getInstance();

  static JavoGeneratorFactory create() {
    return InternalAccess.getInternalGeneratorFactorySupply().get();
  }
}
