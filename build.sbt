import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

ThisBuild / baseVersion := "1.2"

ThisBuild / organization := "org.typelevel"
ThisBuild / organizationName := "Typelevel"

ThisBuild / developers := List(
  Developer("larsrh", "Lars Hupel", "", url("https://github.com/larsrh"))
)

ThisBuild / crossScalaVersions := Seq("2.12.15", "2.13.6", "3.0.2")

ThisBuild / githubWorkflowJavaVersions := Seq("adoptium@8")
ThisBuild / githubWorkflowEnv += ("JABBA_INDEX" -> "https://github.com/typelevel/jdk-index/raw/main/index.json")

ThisBuild / githubWorkflowTargetBranches := Seq("main")

ThisBuild / homepage := Some(url("https://github.com/typelevel/discipline"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/typelevel/discipline"),
    "git@github.com:typelevel/discipline.git"
  )
)

ThisBuild / licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))
ThisBuild / startYear := Some(2013)
ThisBuild / endYear := Some(2021)

ThisBuild / githubWorkflowBuildPreamble := Seq(
  WorkflowStep.Run(List("sudo apt install clang libunwind-dev libgc-dev libre2-dev"))
)

lazy val discipline = project
  .in(file("."))
  .enablePlugins(NoPublishPlugin)
  .aggregate(
    core.jvm,
    core.js,
    core.native
  )

lazy val core = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "discipline",
    moduleName := "discipline-core",
    libraryDependencies += "org.scalacheck" %%% "scalacheck" % "1.15.4"
  )
  .nativeSettings(
    crossScalaVersions := (ThisBuild / crossScalaVersions).value.filter(_.startsWith("2."))
  )
