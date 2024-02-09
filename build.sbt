lazy val playground = (project in file("playground")).settings(
  name := "playground",
  organization := "codynolf",
  version := "1.0",
  scalaVersion := "2.13.12",
  libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1",
  libraryDependencies += "org.scodec" %% "scodec-core" % "1.11.9",
  libraryDependencies ++= Seq( 
    "com.typesafe.akka" %% "akka-http" % "10.5.0",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.8.0",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.0"
  )
)
lazy val scalaAtLightspeedBasics= (project in file("scala-at-lightspeed/1-basics")).settings(
  name := "lightspeed-basic",
  organization := "codynolf",
  version := "1.0",
  scalaVersion := "2.13.12",
  libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1",
  libraryDependencies += "org.scodec" %% "scodec-core" % "1.11.9",
  libraryDependencies ++= Seq( 
    "com.typesafe.akka" %% "akka-http" % "10.5.0",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.8.0",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.0"
  )
)
lazy val scalaAtLightspeedOop = (project in file("scala-at-lightspeed/2-oop")).settings(
  name := "lightspeed-oop",
  organization := "codynolf",
  version := "1.0",
  scalaVersion := "2.13.12",
  libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1",
  libraryDependencies += "org.scodec" %% "scodec-core" % "1.11.9",
  libraryDependencies ++= Seq( 
    "com.typesafe.akka" %% "akka-http" % "10.5.0",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.8.0",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.0"
  )
)
lazy val scalaAtLightspeedFunctional = (project in file("scala-at-lightspeed/3-functional")).settings(
  name := "lightspeed-functional",
  organization := "codynolf",
  version := "1.0",
  scalaVersion := "2.13.12",
  libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1",
  libraryDependencies += "org.scodec" %% "scodec-core" % "1.11.9",
  libraryDependencies ++= Seq( 
    "com.typesafe.akka" %% "akka-http" % "10.5.0",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.8.0",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.0"
  )
)
lazy val scalaAtLightspeedPatternMatching = (project in file("scala-at-lightspeed/4-pattern_matching")).settings(
  name := "lightspeed-patternmatching",
  organization := "codynolf",
  version := "1.0",
  scalaVersion := "2.13.12",
  libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1",
  libraryDependencies += "org.scodec" %% "scodec-core" % "1.11.9",
  libraryDependencies ++= Seq( 
    "com.typesafe.akka" %% "akka-http" % "10.5.0",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.8.0",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.0"
  )
)
lazy val scalaAtLightspeedAdvanced = (project in file("scala-at-lightspeed/5-advanced")).settings(
  name := "lightspeed-advanced",
  organization := "codynolf",
  version := "1.0",
  scalaVersion := "2.13.12",
  libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1",
  libraryDependencies += "org.scodec" %% "scodec-core" % "1.11.9",
  libraryDependencies ++= Seq( 
    "com.typesafe.akka" %% "akka-http" % "10.5.0",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.8.0",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.0"
  )
)
lazy val scalaFpEssentialsBasics= (project in file("scala-functional-programming-essentials/1-basics")).settings(
  name := "essentials-basics",
  organization := "codynolf",
  version := "1.0",
  scalaVersion := "2.13.12",
  libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1",
  libraryDependencies += "org.scodec" %% "scodec-core" % "1.11.9",
  libraryDependencies ++= Seq( 
    "com.typesafe.akka" %% "akka-http" % "10.5.0",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.8.0",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.0"
  )
)

lazy val scalaFpEssentialspeedOop = (project in file("scala-functional-programming-essentials/2-oop")).settings(
  name := "essentials-oop",
  organization := "codynolf",
  version := "1.0",
  scalaVersion := "3.3.1",
  libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.0",
  libraryDependencies += "org.scodec" %% "scodec-core" % "2.2.2"
)

lazy val scalaFpEssentialspeedFp = (project in file("scala-functional-programming-essentials/3-fp")).settings(
  name := "essentials-fp",
  organization := "codynolf",
  version := "1.0",
  scalaVersion := "3.3.1",
  libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.0",
  libraryDependencies += "org.scodec" %% "scodec-core" % "2.2.2"
)