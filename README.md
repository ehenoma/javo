What is *PojoGen*?
------------------

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

This struct represents a *Blogpost*

Wait a minute... What is a Pojo?
--------------------------------
A Pojo is a **Plain old Java Object** that does not need to follow any explicit
*specification*. The *PojoGen* can create *JPA Entities* which are not real
*POJOS*.

Notice
------
This *README* file is not yet finished !!!




