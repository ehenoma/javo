package io.github.pojogen.generator.internal;

import com.google.common.collect.ImmutableList;
import java.util.Collection;

final class InternalClassModel implements InternalGenerationStep {

  private final String className;
  private final Collection<InternalFieldModel> fields;
  private final Collection<InternalConstructorPrototype> constructors;
  private final Collection<InternalMethodModel> methods;

  public InternalClassModel(
      final String className,
      final Collection<InternalFieldModel> fields,
      final Collection<InternalConstructorPrototype> constructors,
      final Collection<InternalMethodModel> methods) {
    this.className = className;
    this.fields = ImmutableList.copyOf(fields);
    this.constructors = ImmutableList.copyOf(constructors);
    this.methods = ImmutableList.copyOf(methods);
  }

  @Override
  public void writeToContext(final InternalGenerationContext buffer) {

  }

  public String getClassName() {
    return this.className;
  }

  public Collection<InternalFieldModel> getFields() {
    return this.fields;
  }

  public Collection<InternalConstructorPrototype> getConstructors() {
    return this.constructors;
  }

  public Collection<InternalMethodModel> getMethods() {
    return this.methods;
  }


}
