ThisBuild / tlBaseVersion := "1.6"

ThisBuild / developers := List(
  tlGitHubDev("larsrh", "Lars Hupel")
)

ThisBuild / crossScalaVersions := Seq("2.12.18", "2.13.10", "3.2.1")
ThisBuild / tlVersionIntroduced := Map("3" -> "1.1.5")

ThisBuild / licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))
ThisBuild / startYear := Some(2013)

ThisBuild / tlJdkRelease := Some(8)

ThisBuild / tlSonatypeUseLegacyHost := false
ThisBuild / tlCiReleaseTags := false

lazy val root = tlCrossRootProject
  .aggregate(core)
  .settings(
    name := "discipline"
  )

lazy val core = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "discipline",
    moduleName := "discipline-core",
    libraryDependencies += "org.scalacheck" %%% "scalacheck" % "1.17.0"
  )
  .jsSettings(
    tlVersionIntroduced ~= {
      _ ++ List("2.12", "2.13").map(_ -> "1.0.2").toMap
    }
  )
  .nativeSettings(
    tlVersionIntroduced := Map(
      "2.12" -> "1.1.3",
      "2.13" -> "1.1.3",
      "3" -> "1.5.0"
    )
  )
