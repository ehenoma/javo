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

package io.github.pojogen.example.console;

import java.io.InputStream;
import java.util.Scanner;
import java.util.function.Consumer;

public final class ConsoleInputAcceptor extends Thread {

  private static final String KEYWORD_STRUCT_END = "@fin";
  private static final String KEYWORD_APP_END = "@stop";

  private final StringBuffer structBuffer;
  private final Consumer<String> structAcceptor;
  private final Scanner scanner;

  ConsoleInputAcceptor(final InputStream delegate, final Consumer<String> structAcceptor) {
    this.scanner = new Scanner(delegate);
    this.structBuffer = new StringBuffer();
    this.structAcceptor = structAcceptor;
  }

  @Override
  public void run() {
    while (this.scanner.hasNextLine()) {
      final String line = this.scanner.nextLine();
      if (line.equalsIgnoreCase(ConsoleInputAcceptor.KEYWORD_APP_END)) {
        break;
      }

      if (line.equalsIgnoreCase(ConsoleInputAcceptor.KEYWORD_STRUCT_END)) {
        this.forwardContentAndClearBuffer();
        return;
      }

      this.structBuffer.append(line).append('\n');
    }

    this.scanner.close();
  }

  private void forwardContentAndClearBuffer() {
    this.structAcceptor.accept(this.getCurrentStructBufferAsString());
    this.structBuffer.setLength(0);
  }

  private String getCurrentStructBufferAsString() {
    return this.structBuffer.toString();
  }
}
