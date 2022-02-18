ThisBuild / tlBaseVersion := "1.4"

ThisBuild / developers := List(
  tlGitHubDev("larsrh", "Lars Hupel")
)

val scala3 = "3.0.2"
ThisBuild / crossScalaVersions := Seq("2.12.15", "2.13.8", scala3)
ThisBuild / tlVersionIntroduced := Map("3" -> "1.1.5")
ThisBuild / githubWorkflowBuildMatrixExclusions +=
  MatrixExclude(Map("scala" -> scala3, "project" -> "rootNative"))

ThisBuild / licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))
ThisBuild / startYear := Some(2013)

lazy val discipline = tlCrossRootProject
    .aggregate(core)
    .settings(name := "discipline")

lazy val core = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "discipline",
    moduleName := "discipline-core",
    libraryDependencies += "org.scalacheck" %%% "scalacheck" % "1.15.4",
    mimaPreviousArtifacts ~= {
      _.filterNot(_.revision == "1.3.0") // cursed
    },
    scalacOptions ++= {
      val version = System.getProperty("java.version")
      val releaseFlag = if (version.startsWith("1.8")) Seq() else Seq("-release", "8")
      val targetFlag = if (tlIsScala3.value || version.startsWith("1.8")) Seq() else Seq("-target:8")
      releaseFlag ++ targetFlag
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
