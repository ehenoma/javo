package io.github.pojogen.parser;

import io.github.pojogen.struct.Struct;

import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;

public interface StructParser {

  Collection<Struct> parse(final InputStream source) throws StructParserException;

  Collection<Struct> parse(final Reader source) throws StructParserException;

  Collection<Struct> parse(final String source) throws StructParserException;

  Struct parseOne(final InputStream source) throws StructParserException;

  Struct parseOne(final Reader source) throws StructParserException;

  Struct parseOne(final String source) throws StructParserException;

}
