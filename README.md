# PojoGen 
[![JDK](https://img.shields.io/badge/java-SE8-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
[![Latest](https://img.shields.io/badge/latest-v1.0-blue.svg)](https://github.com/merlinosayimwen/pojogen)
![LOC](https://sonarcloud.io/api/project_badges/measure?project=io.github.pojogen%3Apojogen-parent&metric=ncloc)

The PojoGen software provides a tool for quick and easy snooping of value objects and beans in Java.
An important goal is to keep the quality and safety of the created classes high. 
For example, for objects that should be immutable, defensive copies are used in the mutator and 
accessors as needed to ensure this immutability. 

***

## ![Blueprint](resources/icon_blueprint.png) Struct Blueprints

The framework has its own language to create so-called struct blueprints. This is case-sensitive,
includes generic wildcards and allows attributes to be written as in UML.
Struct blueprints are used to specify the structure for a value object or JavaBean, which can then be parsed by
the framework and converted into a valid Java source code.

The following source text shows how a valid structure blueprint can be written.
 
```cpp
struct Foo {
  bar: Bar   // Single Bar attribute.
  baz: [Baz] // Array of Baz objects
  qux: <Qux> // Collection of Qux objects.
}
```
For a detailed introduction, there is a [page]() in the wiki.

---

## ![Generator](resources/icon_generator.png) The Generator 
The generator module does the actual job of converting from a blueprint to valid Java source code.
It has a simple interface and is very easily expandable internally. Configuration options are of 
course also given, whereby these come either in the form of flags or properties.

The following text shows how the Builder module can be used to add generator modules to your dependencies.

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
final Logger logger = Logger.getLogger("Pojo Example");
logger.setLevel(Level.ALL);

final PojoGenerator generator = PojoGeneratorFactory.create().getInstance();
final Struct parsedBlueprint = ...

final String generatedClass = generator.generate(parsedBlueprint);
logger.fine(generatedClass);
```

---

 ## ![Quality](resources/icon_quality.png) Code Quality

[![Code Style](https://img.shields.io/badge/codestyle-google-blue.svg)](https://google.github.io/styleguide/javaguide.html)
![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=io.github.pojogen%3Apojogen-parent&metric=alert_status)
![Maintainability](https://sonarcloud.io/api/project_badges/measure?project=io.github.pojogen%3Apojogen-parent&metric=sqale_rating)
![Reliability](https://sonarcloud.io/api/project_badges/measure?project=io.github.pojogen%3Apojogen-parent&metric=reliability_rating)
![Security](https://sonarcloud.io/api/project_badges/measure?project=io.github.pojogen%3Apojogen-parent&metric=security_rating)  

For me, quality and cleanliness in the development of software is very important. This project has a
high standard and uses the most effective and best practices to elegantly solve the addressed problems. 
Third-party software is used for quality control purposes and guaranties a secure, functioning and, 
above all, clean source code. The principles that should be followed when participating are simply
the basic principles of object-oriented design, and there are also project-specific principles and conventions.
The style of the source code is based on the style guidelines of Google, but is somewhat more regulated and concretized.

Parts ot the code that may be upgradeable or not yet documented are reworked.
If there is a suggestion for improvement regarding the source text, please write it to us as an issue.
