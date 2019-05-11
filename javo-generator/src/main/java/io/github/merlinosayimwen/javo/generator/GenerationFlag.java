// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator;

/**
 * Flag which is used during {@code pojo generation} in order to generate a file, while fulfilling
 * given preferences.
 *
 * <p>Flags are used in sections where some operation is either done or not. An example is the
 * {@code LOCAL_VARIABLES_FINAL} flag, which tells the generator, whether local variables should be
 * made final. There is not value to a flag, determining whether it is enabled is used by checking
 * its presence in the {@code GenerationProfile}.
 *
 * @since 1.0
 * @see GenerationProfile
 * @see JavoGenerator
 */
public enum GenerationFlag {

  /**
   * Tells whether local variables should be declared final. The absence of this flag will cause
   * local variables to be declared without use the "final" keyword.
   */
  LOCAL_VARIABLES_FINAL,

  /**
   * Tells whether instance variables should be declared final. Since an object can be immutable
   * without having only "final" fields, really declaring every instance field "final" is a
   * preference and not needed. This flag tells the gen to make all instance variables final.
   */
  INSTANCE_VARIABLES_FINAL,

  /**
   * Tells whether the results of common methods should be cached. Common methods are those, defined
   * in the {@code Object} class. When this flag is set, the generator will cache the results of the
   * {@code hashCode()} and {@code toString()} method and reduce the overhead of calculating those
   * never changing results every time the methods are invoked. This caching is only done, when the
   * {@code struct} or every of its attributes is declared as constant == The pojo is immutable.
   */
  CACHE_COMMON_METHODS,

  /**
   * Tells whether the common methods should be generated at all. Common methods are those, defined
   * in the {@code Object} class. The absence of this flag will lead in no common method to be
   * generated.
   */
  GENERATE_COMMON_METHODS,

  /** Tells whether to use Guava classes. */
  DEPENDENCY_GUAVA
}
