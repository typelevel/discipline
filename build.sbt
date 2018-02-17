import sbtrelease._
import sbtrelease.ReleasePlugin._
import sbtrelease.ReleaseStateTransformations._
import sbtrelease.Utilities._

import com.typesafe.sbt.pgp.PgpKeys._

// Build configuration

name := "discipline root project"

lazy val commonSettings = Seq(
  crossScalaVersions := Seq("2.11.12", "2.12.4", "2.13.0-M3"),
  organization := "org.typelevel",
  name := "discipline",
  scalaVersion := "2.12.4",
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-language:implicitConversions"
  ),
  specs2Version := {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, 13)) =>
        "4.0.2"
      case _ =>
        // don't use 4.0.2 when Scala 2.12, 2.11
        // https://github.com/etorreborre/specs2/issues/636
        "4.0.0"
    }
  },
  libraryDependencies ++= Seq(
    "org.specs2" %%% "specs2-core"       % specs2Version.value % "optional",
    "org.specs2" %%% "specs2-scalacheck" % specs2Version.value % "optional",
    "org.scalacheck" %%% "scalacheck" % "1.13.5",
    "org.scalatest"  %%% "scalatest"  % "3.0.5-M1" % "optional"
  ),
  resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  scalacOptions in Test ++= Seq("-Yrangepos"),

  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    publishSignedArtifacts,
    setNextVersion,
    commitNextVersion
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

val specs2Version = SettingKey[String]("specs2Version")

lazy val root = project.in(file("."))
  .settings(commonSettings: _*)
  .settings(noPublishSettings: _*)
  .aggregate(disciplineJS, disciplineJVM)

lazy val discipline = crossProject.in(file("."))
  .settings(commonSettings: _*)
  .jsSettings(scalaJSStage in Test := FastOptStage)

lazy val disciplineJVM = discipline.jvm
lazy val disciplineJS = discipline.js

// Release plugin

lazy val publishSignedArtifacts = ReleaseStep(
  action = st => {
    val extracted = st.extract
    val ref = extracted.get(thisProjectRef)
    extracted.runAggregated(publishSigned in Global in ref, st)
  },
  check = st => {
    // getPublishTo fails if no publish repository is set up.
    val ex = st.extract
    val ref = ex.get(thisProjectRef)
    Classpaths.getPublishTo(ex.runTask((publishTo in Global in ref), st)._2)
    st
  },
  enableCrossBuild = true
)

lazy val noPublishSettings = Seq(
  publish := (()),
  publishLocal := (()),
  publishArtifact := false
)
