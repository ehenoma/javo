// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal;

public class LineBreakGenerationStep implements GenerationStep {

  @Override
  public void writeToContext(final GenerationContext context) {
    context.getBuffer().writeLine();
  }
}
