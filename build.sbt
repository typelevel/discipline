ThisBuild / tlBaseVersion := "1.7"

ThisBuild / developers := List(
  tlGitHubDev("larsrh", "Lars Hupel")
)

ThisBuild / crossScalaVersions := Seq("2.12.20", "2.13.18", "3.3.7")
ThisBuild / tlVersionIntroduced := Map("3" -> "1.1.5")

ThisBuild / licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))
ThisBuild / startYear := Some(2013)

ThisBuild / tlJdkRelease := Some(8)

lazy val discipline = tlCrossRootProject.aggregate(core)

lazy val core = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "discipline",
    moduleName := "discipline-core",
    libraryDependencies += "org.scalacheck" %%% "scalacheck" % "1.19.0"
  )
  .jsSettings(
    tlVersionIntroduced ~= {
      _ ++ List("2.12", "2.13").map(_ -> "1.0.2").toMap
    }
  )
  .nativeSettings(
    tlVersionIntroduced := List("2.12", "2.13", "3").map(_ -> "1.7.0").toMap
  )
