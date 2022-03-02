ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "AkkaHTTPAssignment"
  )

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.18"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.18"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.2.8"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.8"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test"
libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % "2.6.18"
libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % "10.2.8"
