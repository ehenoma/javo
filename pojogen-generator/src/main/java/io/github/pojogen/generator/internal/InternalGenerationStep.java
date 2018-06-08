package io.github.pojogen.generator.internal;

/**
 *
 *
 * @author Merlin Osayimwen
 * @see InternalPojoGenerator
 * @see InternalGenerationContext
 * @since 1.0
 */
interface InternalGenerationStep {

  /**
   * Writes the {@code steps} representation to the {@code context}.
   *
   * @param context Context that the {@code code representation} is written to.
   */
  void writeToContext(final InternalGenerationContext context);

}
