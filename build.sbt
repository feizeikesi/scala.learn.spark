
name := "scala.learn.spark"

version := "1.0"

scalaVersion := "2.10.6"


libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.10" % "1.6.0" % "provided",
  "org.apache.spark" % "spark-streaming_2.10" % "1.6.0",
  "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.6.0"
)

test in assembly := {}

packAutoSettings