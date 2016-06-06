
name := "scala.learn.spark"

version := "1.0"

scalaVersion := "2.10.6"


/*externalResolvers ++= Seq(
  "Local Maven Repository" at "" + Path.userHome.absolutePath + "/.m2/repository"
)*/

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.10" % "1.6.0" % "provided",
  "org.apache.spark" % "spark-streaming_2.10" % "1.6.0",
  "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.6.0"
)

unmanagedBase := baseDirectory.value / "custom_lib"
/*
assemblyMergeStrategy in assembly := {
  case PathList(ps @ _*) if ps.last endsWith "pom.properties" =>
    MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
*/
//assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala =false, includeDependency =false)
//assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false, includeDependency = false)

assemblyJarName in assembly := "scala-learn-spark.jar"

test in assembly := {}

packAutoSettings