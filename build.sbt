ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .settings(
    name := "carcassonne",
    coverageEnabled := true,
  )
  
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"
libraryDependencies += "javazoom" % "jlayer" % "1.0.1"
libraryDependencies += "org.scalafx" %% "scalafx" % "20.0.0-R31"

enablePlugins(ScoverageSbtPlugin)
enablePlugins(CoverallsPlugin)
