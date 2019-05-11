// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator;


import io.github.merlinosayimwen.javo.Struct;

/**
 * Class which generates POJOs from Struct Blueprints.
 *
 * @since 1.0
 * @see JavoGeneratorFactory
 * @see GenerationFlag
 * @see GenerationProfile
 */
public interface JavoGenerator {

  /**
   * Generates a valid Java Source Code from the {@code blueprint} given.
   *
   * @param struct The blueprint used to generate the file.
   * @return File generated from the {@code model}.
   */
  String generate(Struct struct);

  /**
   * Generates a valid Java Source Code from the {@code blueprint} given.
   *
   * @param struct The blueprint used to generate the file.
   * @param profile Preferences and settings given for the generation.
   * @return File generated from the model.
   */
  String generate(Struct struct, GenerationProfile profile);
}
