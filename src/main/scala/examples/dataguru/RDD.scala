package examples.dataguru

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2016/8/6.
  */
object RDD {
  def main(args: Array[String]) {
    val conf=new  SparkConf()
    val sc=new SparkContext(conf)

    val file =sc.textFile("")


  }
}
