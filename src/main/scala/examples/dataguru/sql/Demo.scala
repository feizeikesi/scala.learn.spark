package examples.dataguru.sql

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Lei on 2016-9-24.
  */
class Demo {
  val conf=new SparkConf().setAppName("Word Count")
  val sc=new SparkContext(conf)


}
