package io.github.pojogen.generator.internal;

import java.util.Collection;

final class InternalConstructorModel extends InternalMethodModel {

  InternalConstructorModel(
      final AccessModifier accessModifier,
      final String className,
      final Collection<InternalFieldModel> parameters) {
    super(accessModifier, className, "", parameters);
  }

  public void writeBodyToContext(final InternalGenerationContext buffer) {
    for (final InternalFieldModel parameter : parameters) {
      //TODO: Check whether type is of array or collection and should be shallow copied.
      buffer.append("this.").append(parameter.getName()).append(" = ")
          .append(parameter.getName());
    }
  }

}
