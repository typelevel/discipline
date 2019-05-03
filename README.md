discipline
==========

[![Join the chat at https://gitter.im/typelevel/discipline](https://badges.gitter.im/typelevel/discipline.svg)](https://gitter.im/typelevel/discipline?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Flexible law checking for Scala

[![Build Status](https://travis-ci.org/typelevel/discipline.png?branch=master)](http://travis-ci.org/typelevel/discipline)


Usage
-----

This library is currently available for Scala binary versions 2.11 and 2.12.

To use the latest version, include the following in your `build.sbt`:

```scala
libraryDependencies +=
  "org.typelevel" %% "discipline" % "0.10.0"
```

For a little more info see the ["Law Enforcement using Discipline"](https://typelevel.org/blog/2013/11/17/discipline.html) blog post.

Binding to test frameworks
--------------------------

Discipline is built against ScalaCheck 1.14.x. There is also a published artifact for scala.js.

There are bindings for Specs2 and ScalaTest. Since Discipline depends on them optionally, you have to add either one to your build explicitly:

```scala
libraryDependencies +=
  "org.scalatest" %% "scalatest" % "3.0.5"
  // or
  "org.specs2" %% "specs2-scalacheck" % "4.3.0"
```

Code of Conduct
---------------

See the [Code of Conduct](CODE_OF_CONDUCT.md)
