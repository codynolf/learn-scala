scalaVersion := "2.13.12"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"

lazy val playground = (project in file("playground")).settings(
  name := "playground",
  organization := "ch.epfl.scala",
  version := "1.0",
  libraryDependencies += "org.scodec" %% "scodec-core" % "1.11.9",
  libraryDependencies ++= Seq( 
    "com.typesafe.akka" %% "akka-http" % "10.5.0",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.8.0",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.0"
  )
)
lazy val scalaAtLightspeedBasics= (project in file("scala-at-lightspeed/1-basics"))
lazy val scalaAtLightspeedOop = (project in file("scala-at-lightspeed/2-oop"))
lazy val scalaAtLightspeedFunctional = (project in file("scala-at-lightspeed/3-functional"))
lazy val scalaAtLightspeedPatternMatching = (project in file("scala-at-lightspeed/4-pattern_matching"))
lazy val scalaAtLightspeedAdvanced = (project in file("scala-at-lightspeed/5-advanced"))
lazy val scalaFpEssentialsBasics= (project in file("scala-functional-programming-essentials/1-basics"))
lazy val scalaFpEssentialspeedOop = (project in file("scala-functional-programming-essentials/2-oop"))

