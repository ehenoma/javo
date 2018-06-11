[![Picture](http://icons.iconarchive.com/icons/andreasmyrup/cubicons/256/blueprint-icon.png)](https://github.com/merlinosayimwen/pojogen)

What is *PojoGen*?
------------------
[![Java Version](https://img.shields.io/badge/java-v1.8-blue.svg)](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
[![Latest](https://img.shields.io/badge/latest-v1.0-blue.svg)](https://github.com/merlinosayimwen/pojogen)
[![License](https://img.shields.io/badge/license-apache--2.0-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

The *PojoGen-Framework* is aiming to allow fast creation and highly
configurable creation of *Java object classes* with optional *specifications*.
The framework is divided into multiple modules in order to use its contents
in different *use-cases*. The code of the *framework* is using
[Google's Java Coding Style](https://google.github.io/styleguide/javaguide.html)
and mostly follows the [SOLID](https://en.wikipedia.org/wiki/SOLID) princples.

So called *Structs* are providing the *Blueprints* to create *POJOs*.
The syntax of a *struct* is fairly simple and *tabs* are allowed. The
current syntax of a defined *struct* with one *attribute* looks as follows:
```
[modifier] struct (Name) {
  [modifier] (attributeName) : (AttributeType)[;]
  ...
}
```

An implementation of the *struct specifications* might look like this:

```
struct Blogpost {
  const id : int
  creationDate : Date

  title : String
  content : String

  rating : float
  readersComments : Collection<Comment>
}
```

The shown struct represents a *Blogpost*

Getting started
--

Following shows how you can add the `pojogen-generator` module to your dependencies when using a common *build tool*.

To add it to your **maven** project use following code:
```xml
<dependency>
  <groupId>io.github.pojogen</groupId>
  <artifactId>pojogen-generator</artifactId>
  <version>1.0</version>
</dependency>
```

If you use **gradle** this is the code for you:
```groovy
dependencies {
  compile 'io.github.pojogen:pojogen-generator:1.0'
}
```

Links
--
- https://de.wikipedia.org/wiki/Plain_Old_Java_Object
