name := "scalikejdbc-generic"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2",
  "org.scalikejdbc" %% "scalikejdbc" % "3.0.0",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "com.h2database" % "h2" % "1.4.195" % "test"
)

organization := "net.pishen"
licenses += ("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.html"))
