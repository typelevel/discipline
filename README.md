discipline
==========

Flexible law checking for Scala

[![Build Status](https://travis-ci.org/typelevel/discipline.png?branch=master)](http://travis-ci.org/typelevel/discipline)


Usage
-----

This library is currently available for Scala binary versions 2.10 and 2.11.

To use the latest version, include the following in your `build.sbt`:

```scala
libraryDependencies +=
  "org.typelevel" %% "discipline" % "0.2.1"
```


Binding to test frameworks
--------------------------

Discipline is built against Scalacheck 1.11.x.

There are bindings for Specs2 and ScalaTest. Since Discipline depends on them optionally, you have to add either one to your build explicitly:

```scala
libraryDependencies +=
  "org.scalatest" %% "scalatest" % "2.1.3"
  // or
  "org.specs2" %% "specs2" % "2.3.11"
```
