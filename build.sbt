val scala3Version = "3.1.1"

val akkaHttpVersion = "10.2.9"
val akkaVersion    = "2.6.19"
val clamAVClientVersion = "2.1.2"
val logBackVersion = "1.2.11"
val scalaCheckVersion = "3.2.9"
val scalaCheckPlusVersion = "3.2.9.0"
val slf4jVersion = "1.7.36"
val zioVersion =  "1.0.12"

ThisBuild / scalacOptions ++=
  Seq(
    "-deprecation",
    "-feature",
    "-language:implicitConversions",
    "-unchecked",
    "-Xfatal-warnings",
    "-Yexplicit-nulls",
    "-Ykind-projector",
    "-Ysafe-init", // Protect against forward access reference
  ) ++ Seq("-rewrite", "-indent") ++ Seq("-source", "future")

lazy val commonSettings = Seq(
  update / evictionWarningOptions := EvictionWarningOptions.empty,
  Compile / console / scalacOptions --= Seq(
    "-Wunused:_",
    "-Xfatal-warnings",
  ),
  Test / console / scalacOptions :=
    (Compile / console / scalacOptions).value,
)

val akka = Seq (
  "com.typesafe.akka" %% "akka-actor-typed",
  "com.typesafe.akka" %% "akka-stream"
).map(_ % akkaVersion) ++ Seq(
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test
)

val akkaHttp = Seq (
  "com.typesafe.akka" %% "akka-http"                % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion % Test,
).map(_.cross(CrossVersion.for3Use2_13))

// See https://github.com/cdarras/clamav-client
val clamAVClient = Seq ("xyz.capybara" % "clamav-client" % clamAVClientVersion)

val logging = Seq(
  "ch.qos.logback" % "logback-classic"
).map(_ % logBackVersion)

val testDependencies = Seq(
    "dev.zio" %% "zio-test" % zioVersion,
    "org.scalatest" %% "scalatest" % scalaCheckVersion,
    "org.scalatestplus" %% "scalacheck-1-15" % scalaCheckPlusVersion
  ).map(_ % Test)

val zio = Seq(
        "dev.zio" %% "zio",
        "dev.zio" %% "zio-streams"
  ).map(_ % zioVersion)

lazy val dependencies = Seq(
  libraryDependencies ++= akka ++ akkaHttp ++ clamAVClient ++ logging ++ zio ++ testDependencies
)

lazy val root = project
  .in(file("."))
  .settings(
    organization := "io.orite",
    name := "clamav",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version   
  )
  .settings(commonSettings)
  .settings(dependencies)

