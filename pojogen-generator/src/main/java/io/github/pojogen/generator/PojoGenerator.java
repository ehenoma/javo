/*
 * Copyright 2018 Merlin Osayimwen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.pojogen.generator;

import io.github.pojogen.struct.Struct;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;

public interface PojoGenerator {

  String generatePojoFromStruct(final Struct source);

  String generatePojoFromStruct(final Struct source, final GenerationProfile profile);

  void generateToFile(final Struct source, final GenerationProfile profile, final Path destination);

  void generateToFile(final Struct source, final GenerationProfile profile, final File destination);

  void generateToFile(
      final Struct source, final GenerationProfile profile, final FileOutputStream destination);
}
