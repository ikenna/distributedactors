import sbt.Keys._

name := "fsm"

version := "1.0"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.4",
  "com.typesafe.akka" %% "akka-slf4j" % "2.3.4",
  "ch.qos.logback" % "logback-classic" % "1.0.13"
)
