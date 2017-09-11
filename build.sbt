lazy val commonSettings = inThisBuild(
  List(
    organization := "com.whisk",
    scalaVersion := "2.12.3",
    version := "0.1.0",
    scalacOptions ++= Seq("-feature", "-deprecation", "-language:implicitConversions"),
    sonatypeProfileName := "com.whisk",
    publishMavenStyle := true,
    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
    homepage := Some(url("https://github.com/whisklabs/mysql-util")),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/whisklabs/mysql-util"),
        "scm:git:github.com/whisklabs/mysql-util.git"
      )
    ),
    developers := List(
      Developer(id = "viktortnk",
                name = "Viktor Taranenko",
                email = "viktortnk@gmail.com",
                url = url("https://finelydistributed.io/"))
    ),
    publishTo := Some(Opts.resolver.sonatypeStaging)
  ))

lazy val root = project
  .in(file("."))
  .settings(commonSettings)
  .settings(publish := {}, publishLocal := {}, packagedArtifacts := Map.empty)
  .aggregate(core, circe)

lazy val core = project
  .in(file("mysql-util-core"))
  .settings(
    name := "mysql-util-core",
    commonSettings,
    libraryDependencies ++= Seq(
      "com.twitter" %% "finagle-mysql" % "7.1.0",
      "org.scalatest" %% "scalatest" % "3.0.4" % Test,
      "com.whisk" %% "docker-testkit-core" % "0.10.0-beta0" % Test,
      "org.jdbi" % "jdbi3" % "3.0.0-beta2" % Test,
      "mysql" % "mysql-connector-java" % "5.1.44" % Test
    )
  )

lazy val circe = project
  .in(file("mysql-util-circe"))
  .settings(
    name := "mysql-util-circe",
    commonSettings,
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % "0.9.0-M1",
      "io.circe" %% "circe-parser" % "0.9.0-M1"
    )
  )
  .dependsOn(core % "compile->compile;test->test")
