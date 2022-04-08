ThisBuild / tlBaseVersion := "1.5"

ThisBuild / developers := List(
  tlGitHubDev("larsrh", "Lars Hupel")
)

val scala3 = "3.1.1"
ThisBuild / crossScalaVersions := Seq("2.12.15", "2.13.8", scala3)
ThisBuild / tlVersionIntroduced := Map("3" -> "1.1.5")
ThisBuild / githubWorkflowBuildMatrixExclusions +=
  MatrixExclude(Map("scala" -> scala3, "project" -> "rootNative"))

ThisBuild / licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))
ThisBuild / startYear := Some(2013)

lazy val root = tlCrossRootProject.aggregate(core)

lazy val core = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "discipline",
    moduleName := "discipline-core",
    libraryDependencies += "org.scalacheck" %%% "scalacheck" % "1.16.0",
    mimaPreviousArtifacts ~= {
      _.filterNot(_.revision == "1.3.0") // cursed
    }
  )
  .jsSettings(
    tlVersionIntroduced ~= {
      _ ++ List("2.12", "2.13").map(_ -> "1.0.2").toMap
    }
  )
  .nativeSettings(
    tlVersionIntroduced ~= {
      _ ++ List("2.12", "2.13").map(_ -> "1.1.3").toMap
    },
    crossScalaVersions := (ThisBuild / crossScalaVersions).value.filter(_.startsWith("2."))
  )
