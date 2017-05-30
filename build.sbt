name := "scalikejdbc-generic"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2",
  "org.scalikejdbc" %% "scalikejdbc" % "3.0.0",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "com.h2database" % "h2" % "1.4.195" % "test"
)
