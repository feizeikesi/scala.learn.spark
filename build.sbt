
name := "scala.learn.spark"

version := "1.0"

scalaVersion := "2.10.6"


libraryDependencies ++= Seq(
  "com.maxmind.geoip2" % "geoip2" % "2.3.1",

  "org.apache.spark" % "spark-core_2.10" % "1.6.0" % "provided",
  "org.apache.spark" % "spark-streaming_2.10" % "1.6.0",
  "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.6.0",
  "org.apache.spark" % "spark-sql_2.10" % "1.6.0",
  "org.apache.spark" % "spark-mllib_2.10" % "1.6.0",

  "com.twitter" % "util-collection_2.10" % "6.34.0"
)

packAutoSettings