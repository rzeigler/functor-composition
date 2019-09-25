val circeVersion = "0.10.0"
val circe = Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

val settings = Seq(
    scalaVersion := "2.12.8",
    scalacOptions ++= Seq(
        "-deprecation",
        "-encoding", "UTF-8",
        "-feature",
        "-language:higherKinds",
        "-language:implicitConversions", "-language:existentials",
        "-unchecked",
        "-Yno-adapted-args",
        "-Ywarn-numeric-widen",
        "-Ywarn-value-discard",
        "-Ypartial-unification",
        "-Xfuture"
      ),
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "1.6.0",
      "org.typelevel" %% "cats-effect" % "1.3.0",
      "org.typelevel" %% "spire" % "0.16.1",
      compilerPlugin("org.typelevel" %% "kind-projector" % "0.10.1")
    ) ++ circe
)


resolvers += Resolver.sonatypeRepo("releases")

enablePlugins(TutPlugin)

lazy val scalaTypeclasses = project.in(file("."))
  .settings(moduleName := "scala-typeclasses")
  .settings(settings: _*)
  .aggregate(core, slides)
  .dependsOn(core, slides)

lazy val core = project
  .settings(moduleName := "scala-typeclasses-core")
  .settings(settings: _*)


lazy val slides = project
  .settings(moduleName := "scala-typeclasses-slides")
  .settings(settings: _*)
  .settings(
    tutTargetDirectory := baseDirectory.value / "../docs",
    watchSources ++= (tutSourceDirectory.value ** "*.html").get
  ).dependsOn(core)
  .enablePlugins(TutPlugin)