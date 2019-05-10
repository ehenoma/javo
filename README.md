# Javo
[![JDK](https://img.shields.io/badge/java-SE8-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
[![Latest](https://img.shields.io/badge/latest-v1.0-blue.svg)](https://github.com/merlinosayimwen/pojogen)

Javo *(Java Value Objects)* is a tool for quick and easy creation of value objects and beans in Java.
An important goal is to keep the quality and safety of the created classes high. 
For example, for objects that should be immutable, defensive copies are used in the mutator and 
accessors as needed to ensure this immutability. 

The software was fomerly known as Pojogen.
***

## Struct Blueprints

The framework has its own language to create so-called struct blueprints. This is case-sensitive,
includes generic wildcards and allows attributes to be written as in UML.
Struct blueprints are used to specify the structure for a value object or Java Bean, which can then be parsed by
the framework and converted into a valid Java source code.

The following source text shows how a valid structure blueprint can be written.
 
```cpp
struct Foo {
  bar: Bar   // Single Bar attribute.
  baz: [Baz] // Array of Baz objects
  qux: <Qux> // Collection of Qux objects.
}
```
For a detailed introduction, read the [specification](docs/structs.md).

---

## The Generator 
The generator module does the actual job of converting from a blueprint to valid Java source code.
It has a simple interface and is very easily expandable internally. Configuration options are of 
course also given, whereby these come either in the form of flags or properties.

The following text shows how common BuildTools can be used to add the generator modules to your dependencies.

##### Maven
```xml
<dependency>
  <groupId>io.github.pojogen</groupId>
  <artifactId>pojogen-generator</artifactId>
  <version>1.0</version>
</dependency>
```
##### Gradle
```groovy
compile group: 'io.github.pojogen', name: 'pojogen-generator', version: '1.0'
```
##### Ivy
```ivy
<dependency org="io.github.pojogen" name="pojogen-generator" rev="1.0"/>
```

The following example illustrates how the generator can be used to convert an already 
parsed blueprint into a Java class.

```java
Logger logger = Logger.getLogger("Pojo Example");
logger.setLevel(Level.ALL);

PojoGenerator generator = PojoGeneratorFactory.create().getInstance();
Struct parsed = // Your parsed struct.

String generatedCode = generator.generate(parserd);
logger.fine(generatedCode);
```

