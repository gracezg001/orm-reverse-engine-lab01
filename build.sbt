ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"

lazy val root = (project in file("."))
  .settings(
    name := "orm-reverse-engine-lab01"
  )


libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % "4.0.0" ,

  "org.postgresql" % "postgresql" % "42.5.1"  ,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
"org.scalatest" %% "scalatest" % "3.2.14" % Test,
"org.specs2" %% "specs2-core" % "4.19.0" % Test

)

enablePlugins(ScalikejdbcPlugin)