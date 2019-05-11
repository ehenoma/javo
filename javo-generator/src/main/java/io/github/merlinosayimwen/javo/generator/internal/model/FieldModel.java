// Copyright 2019 Merlin Osayimwen. All rights reserved.
// Use of this source code is governed by a MIT-style
// license that can be found in the LICENSE file.

package io.github.merlinosayimwen.javo.generator.internal.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import io.github.merlinosayimwen.javo.StructAttribute;
import io.github.merlinosayimwen.javo.generator.internal.GenerationContext;
import io.github.merlinosayimwen.javo.generator.internal.GenerationStep;
import io.github.merlinosayimwen.javo.generator.internal.type.ReferenceType;
import io.github.merlinosayimwen.javo.generator.internal.type.ReferenceTypeParser;

public final class FieldModel extends VariableModel implements GenerationStep {

  private static final ReferenceTypeParser REFERENCE_TYPE_PARSER = ReferenceTypeParser.create();

  private final AccessModifier accessModifier;

  private FieldModel(
      final String name,
      final ReferenceType type,
      final boolean modifiable,
      final AccessModifier accessModifier) {

    super(name, type, modifiable);
    this.accessModifier = accessModifier;
  }

  @Override
  public final void writeToContext(final GenerationContext context) {
    this.accessModifier.getKeyword().ifPresent(keyword -> context.getBuffer().write(keyword + " "));
    super.writeToContext(context);
    context.getBuffer().writeLine(";");
  }

  public AccessModifier getAccessModifier() {
    return this.accessModifier;
  }

  @Override
  public boolean equals(final Object checkTarget) {
    if (!super.equals(checkTarget)) {
      return false;
    }

    if (!(checkTarget instanceof FieldModel)) {
      return false;
    }

    return this.accessModifier.equals(((FieldModel) checkTarget).accessModifier);
  }

  @Override
  public int hashCode() {
    return super.hashCode() + this.accessModifier.hashCode();
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", this.name)
        .add("typeName", this.type)
        .add("modifiable", this.modifiable)
        .add("access-modifier", this.accessModifier)
        .toString();
  }

  public static FieldModel fromVariable(
      final VariableModel variable, final AccessModifier accessModifier) {

    Preconditions.checkNotNull(variable);
    Preconditions.checkNotNull(accessModifier);
    return new FieldModel(
        variable.getName(), variable.getType(), variable.isModifiable(), accessModifier);
  }

  public static FieldModel fromStructAttribute(StructAttribute attribute) {
    Preconditions.checkNotNull(attribute);

    final ReferenceType type =
        FieldModel.REFERENCE_TYPE_PARSER.parseReference(attribute.getTypeName());

    return fromVariable(
        VariableModel.create(attribute.getName(), type, !attribute.isImmutable()),
        AccessModifier.PRIVATE);
  }
}
