import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {
  val appName = "RoomR"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq( // Add your project dependencies here,
    "com.google.inject" % "guice" % "3.0",
    "com.typesafe" % "play-plugins-guice" % "2.0.2",
    //"com.google.guava" % "guava" % "[11.0.0,12.0.0[",
    "joda-time" % "joda-time" % "[2.0,3.0[",
    "org.joda" % "joda-money" % "[0.6,1.0[",
    "org.apache.httpcomponents" % "httpclient" % "4.1.3",
    "org.mockito" % "mockito-all" % "1.9.0")

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings( // Add your own project settings here      
  )
}
