import sbtrelease._
import sbtrelease.ReleasePlugin._
import sbtrelease.ReleaseStateTransformations._
import sbtrelease.Utilities._
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

// GHA configuration

val scala211 = "2.11.12"

ThisBuild / githubWorkflowPublishTargetBranches := Seq()

ThisBuild / crossScalaVersions := Seq(scala211, "2.12.10", "2.13.1", "0.27.0-RC1", "3.0.0-M1")

ThisBuild / githubWorkflowBuildPreamble := Seq(
  WorkflowStep.Run(List("sudo apt install clang libunwind-dev libgc-dev libre2-dev"))
)

// Build configuration

name := "discipline root project"

lazy val commonSettings = Seq(
  organization := "org.typelevel",
  name := "discipline",
  scalaVersion := "2.13.1",
  crossScalaVersions := (ThisBuild / crossScalaVersions).value,
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-language:implicitConversions"
  ),
  libraryDependencies += "org.scalacheck" %%% "scalacheck" % "1.15.1",
  releaseCrossBuild := true,
  releaseVcsSign := true,
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    publishArtifacts,
    releaseStepCommandAndRemaining(s"; ++ ${scala211} ; coreNative/publish"),
    setNextVersion,
    commitNextVersion,
    releaseStepCommand("sonatypeRelease")
  ),
  // Publishing
  publishTo := version.apply { v =>
    val nexus = "https://oss.sonatype.org/"
    if (v.trim.endsWith("SNAPSHOT"))
      Some("Snapshots".at(nexus + "content/repositories/snapshots"))
    else
      Some("Releases".at(nexus + "service/local/staging/deploy/maven2"))
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

lazy val commonNativeSettings = Seq(
  scalaVersion := scala211,
  crossScalaVersions := Seq(scala211)
)

lazy val root = project
  .in(file("."))
  .settings(commonSettings)
  .settings(crossScalaVersions := Seq())
  .settings(noPublishSettings)
  .aggregate(
    coreJS,
    coreJVM,
    coreNative
  )

lazy val core = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(commonSettings)
  .settings(
    moduleName := "discipline-core"
  )
  .jsSettings(
    scalaJSStage in Test := FastOptStage,
  )
  .nativeSettings(
    commonNativeSettings
  )

lazy val coreJVM = core.jvm
lazy val coreJS = core.js
lazy val coreNative = core.native

// Release plugin

lazy val noPublishSettings = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false
)
