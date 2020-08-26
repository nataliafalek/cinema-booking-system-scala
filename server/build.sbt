scalaVersion := "2.13.3"

name := "cinema-booking-system"
organization := "com.faleknatalia"
version := "1.0"

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.0"
val SlickVersion = "3.3.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.typesafe.slick" %% "slick" % SlickVersion,
  "org.slf4j" % "slf4j-nop" % "1.7.30",
  "com.typesafe.slick" %% "slick-hikaricp" % SlickVersion,
  "org.postgresql" % "postgresql" % "42.2.16",
  "ch.megard" %% "akka-http-cors" % "1.1.0"
)