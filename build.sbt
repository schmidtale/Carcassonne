ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .settings(
    name := "carcassonne",
    coverageEnabled := true,
    coverageExcludedPackages := ".*carcassonne;.*_view.*",
  )
  
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"
libraryDependencies += "javazoom" % "jlayer" % "1.0.1"
libraryDependencies += "org.scalafx" %% "scalafx" % "20.0.0-R31"
libraryDependencies ++= Seq(
  "org.apache.xmlgraphics" % "batik-transcoder" % "1.17",
  "org.apache.xmlgraphics" % "batik-codec" % "1.17",
  "org.scala-lang.modules" %% "scala-xml" % "2.3.0",
  "org.playframework" %% "play-json" % "3.0.4"
)

libraryDependencies ++= {
  // Determine OS version of JavaFX binaries
  lazy val osName = System.getProperty("os.name") match {
    case n if n.startsWith("Linux") => "linux"
    case n if n.startsWith("Mac") => "mac"
    case n if n.startsWith("Windows") => "win"
    case _ => throw new Exception("Unknown platform!")
  }
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % "20" classifier osName)
}

// JDK Compatibility
javacOptions ++= Seq("--release", "22")
Compile / run / javaOptions ++= Seq(
    "--module-path", (Compile / fullClasspath).value.map(_.data).mkString(";"),
    "--add-modules", "javafx.controls,javafx.fxml"
)

enablePlugins(ScoverageSbtPlugin)
enablePlugins(CoverallsPlugin)
