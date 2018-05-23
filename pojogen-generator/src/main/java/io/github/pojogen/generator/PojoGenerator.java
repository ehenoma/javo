package io.github.pojogen.generator;

import io.github.pojogen.struct.Struct;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;

public interface PojoGenerator {

  String generatePojoFromStruct(final Struct source);

  String generatePojoFromStruct(final Struct source, final GenerationProfile profile);

  void generateToFile(final Struct source, final GenerationProfile profile,
      final Path destination);

  void generateToFile(final Struct source, final GenerationProfile profile,
      final File destination);

  void generateToFile(final Struct source, final GenerationProfile profile,
      final FileOutputStream destination);

}
