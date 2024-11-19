ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .settings(
    name := "carcassonne",
    coverageEnabled := true,
  )
  
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"

enablePlugins(ScoverageSbtPlugin)
enablePlugins(CoverallsPlugin)
coverageReportDirectory := baseDirectory.value / "target" / "scala-3.3.4" / "coverage-report"
