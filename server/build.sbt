scalaVersion := "2.13.3"

name := "cinema-booking-system"
organization := "com.faleknatalia"
version := "1.0"

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.0"
val SlickVersion = "3.3.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.typesafe.slick" %% "slick" % SlickVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.slick" %% "slick-hikaricp" % SlickVersion,
  "org.postgresql" % "postgresql" % "42.2.16",
  "ch.megard" %% "akka-http-cors" % "1.1.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
)