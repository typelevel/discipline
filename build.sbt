import sbtrelease._
import sbtrelease.ReleasePlugin._
import sbtrelease.ReleaseStateTransformations._
import sbtrelease.Utilities._
import sbtcrossproject.crossProject
import com.typesafe.sbt.pgp.PgpKeys._

// Build configuration

name := "discipline root project"

lazy val commonSettings = Seq(
  crossScalaVersions := Seq("2.11.12", "2.12.8", "2.13.0-RC1"),
  organization := "org.typelevel",
  name := "discipline",
  scalaVersion := "2.12.8",
  scalacOptions ++= Seq(
    "-Xfuture",
    "-deprecation",
    "-feature",
    "-language:implicitConversions"
  ),
  libraryDependencies ++= Seq(
    "org.scalacheck" %%% "scalacheck" % "1.14.0",
    "org.scalatest" %%% "scalatest" % "3.1.0-SNAP9" % "optional",
    "org.scalatestplus" %%% "scalatestplus-scalacheck" % "1.0.0-SNAP4" % "optional",
    "org.specs2" %%% "specs2-scalacheck" % "4.5.1" % "optional"
  ),
  scalacOptions in Test ++= Seq("-Yrangepos"),

  releaseCrossBuild := true,
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    publishArtifacts,
    setNextVersion,
    commitNextVersion,
    releaseStepCommand("sonatypeRelease")
  ),

  // Publishing

  publishTo := (version).apply { v =>
    val nexus = "https://oss.sonatype.org/"
    if (v.trim.endsWith("SNAPSHOT"))
      Some("Snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("Releases" at nexus + "service/local/staging/deploy/maven2")
  }.value,

  credentials += {
    Seq("build.publish.user", "build.publish.password").map(k => Option(System.getProperty(k))) match {
      case Seq(Some(user), Some(pass)) =>
        Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", user, pass)
      case _ =>
        Credentials(Path.userHome / ".ivy2" / ".credentials")
    }
  },

  pomIncludeRepository := Function.const(false),

  pomExtra := (
    <url>https://github.com/typelevel/discipline</url>
    <licenses>
      <license>
        <name>MIT</name>
        <url>http://www.opensource.org/licenses/mit-license.php</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>https://github.com/typelevel/discipline</url>
      <connection>scm:git:git://github.com/typelevel/discipline.git</connection>
    </scm>
    <developers>
      <developer>
        <id>larsrh</id>
        <name>Lars Hupel</name>
        <url>https://github.com/larsrh</url>
      </developer>
    </developers>
  )
)

lazy val root = project.in(file("."))
  .settings(commonSettings: _*)
  .settings(noPublishSettings: _*)
  .aggregate(disciplineJS, disciplineJVM)

lazy val discipline = crossProject(JSPlatform, JVMPlatform).in(file("."))
  .settings(commonSettings: _*)
  .jsSettings(scalaJSStage in Test := FastOptStage)

lazy val disciplineJVM = discipline.jvm
lazy val disciplineJS = discipline.js

// Release plugin

lazy val noPublishSettings = Seq(
  publish := (()),
  publishLocal := (()),
  publishArtifact := false
)
