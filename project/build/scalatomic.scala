import sbt._

class Scalatomic(info: ProjectInfo) extends DefaultProject(info) {

  val mavenLocal = "Local Maven Repository" at "file://" + Path.userHome + "/.m2/repository"

  val specs = "org.scala-tools.testing" % "specs_2.8.1" % "1.6.7" % "test->default"
}
