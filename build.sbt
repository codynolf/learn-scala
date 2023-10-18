scalaVersion := "2.13.12"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"

lazy val playground = (project in file("playground"))
lazy val scalaAtLightspeedBasics= (project in file("scala-at-lightspeed/1-basics"))
lazy val scalaAtLightspeedOop = (project in file("scala-at-lightspeed/2-oop"))
lazy val scalaAtLightspeedFunctional = (project in file("scala-at-lightspeed/3-functional"))
lazy val scalaAtLightspeedPatternMatching = (project in file("scala-at-lightspeed/4-pattern_matching"))
lazy val scalaAtLightspeedAdvanced = (project in file("scala-at-lightspeed/5-advanced"))