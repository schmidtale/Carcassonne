ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .settings(
    name := "carcassonne",
    coverageEnabled := true,
	addCommandAlias("coveralls", ";!coverageAggregate;coveralls")
  )
  
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"

enablePlugins(ScoverageSbtPlugin)
enablePlugins(CoverallsPlugin)
parallelExecution in Global := false
