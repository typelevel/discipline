organization := "org.typelevel"

name := "discipline"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.3"

scalacOptions ++= Seq(
  "-feature",
  "-language:implicitConversions"
)

scalacOptions in Test += "-Yrangepos"

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.10.1",
  "org.scalatest" %% "scalatest" % "2.0" % "optional",
  "org.specs2" %% "specs2" % "2.3.2" % "optional"
)

publishTo <<= (version).apply { v =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("Snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("Releases" at nexus + "service/local/staging/deploy/maven2")
}

credentials += {
  Seq("build.publish.user", "build.publish.password").map(k => Option(System.getProperty(k))) match {
    case Seq(Some(user), Some(pass)) =>
      Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", user, pass)
    case _ =>
      Credentials(Path.userHome / ".ivy2" / ".credentials")
  }
}

pomIncludeRepository := Function.const(false)

pomExtra := (
  <url>http://typelevel.org/scalaz</url>
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

// vim: expandtab:ts=2:sw=2
