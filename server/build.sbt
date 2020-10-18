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
  "com.typesafe.slick" %% "slick-hikaricp" % SlickVersion,
  "com.typesafe" % "config" % "1.4.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.postgresql" % "postgresql" % "42.2.16",
  "ch.megard" %% "akka-http-cors" % "1.1.0",
  "org.apache.pdfbox" % "pdfbox" % "2.0.21",
  "com.sun.mail" % "javax.mail" % "1.6.2",
  "javax.mail" % "javax.mail-api" % "1.6.2",
  "com.sun.activation" % "javax.activation" % "1.2.0",

  "org.scalatest" %% "scalatest" % "3.2.2" % Test
)