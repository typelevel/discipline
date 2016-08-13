discipline
==========

Flexible law checking for Scala

[![Build Status](https://travis-ci.org/typelevel/discipline.png?branch=master)](http://travis-ci.org/typelevel/discipline)


Usage
-----

This library is currently available for Scala binary versions 2.10, 2.11 and 2.12.0-M5.

To use the latest version, include the following in your `build.sbt`:

```scala
libraryDependencies +=
  "org.typelevel" %% "discipline" % "0.5"
```


Binding to test frameworks
--------------------------

Discipline is built against Scalacheck 1.13.x. There is also a published artifact for scala.js.

There are bindings for Specs2 and ScalaTest. Since Discipline depends on them optionally, you have to add either one to your build explicitly:

```scala
libraryDependencies +=
  "org.scalatest" %% "scalatest" % "3.0.0"
  // or
  "org.specs2" %% "specs2" % "3.8.4"
```
