discipline
==========

[![Join the chat at https://gitter.im/typelevel/discipline](https://badges.gitter.im/typelevel/discipline.svg)](https://gitter.im/typelevel/discipline?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
![Continuous Integration](https://github.com/typelevel/discipline/workflows/Continuous%20Integration/badge.svg)

Flexible law checking for Scala


Usage
-----

This library is currently available for Scala binary versions 2.12, 2.13 and 3.0.

To use the latest version, include the following in your `build.sbt`:

```scala
libraryDependencies +=
  "org.typelevel" %% "discipline-core" % "1.2.0"
```

For a little more info see the ["Law Enforcement using Discipline"](https://typelevel.org/blog/2013/11/17/discipline.html) blog post.

Binding to test frameworks
--------------------------

Discipline is built against ScalaCheck 1.15.x. There is also a published artifact for Scala.js and Scala Native.

`discipline-core` is required early each Scala release cycle, so we have chosen to keep this repo minimal.  Starting with v1.0.0, please find the framework bindings in their new locations:

* [discipline-scalatest](https://github.com/typelevel/discipline-scalatest)
* [discipline-specs2](https://github.com/typelevel/discipline-specs2)
* [discipline-munit](https://github.com/typelevel/discipline-munit)

Code of Conduct
---------------

See the [Code of Conduct](CODE_OF_CONDUCT.md)
