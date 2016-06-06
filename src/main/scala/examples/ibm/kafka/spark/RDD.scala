package examples.ibm.kafka.spark

import org.apache.spark.{ SparkConf, SparkContext }


/**
  * 第三章 RDD编程
  * Created by Lei on 2016-5-29.
  */
object RDD {
  //spark-submit --master yarn --jars $(echo lib/*.jar|tr ' ' ',') --master yarn  --class examples.ibm.kafka.spark.RDD scala-learn-spark_2.10-1.0.jar
  def main (args : Array[String]) {
    /*
    #Software: Microsoft Internet Information Services 7.5
    #Version: 1.0
    #Date: 2016-05-24 00:00:00
    #Fields: date time s-sitename cs-method cs-uri-stem cs-uri-query cs-username c-ip cs-version cs(User-Agent) cs(Cookie) cs(Referer) cs-host sc-status sc-bytes time-taken
    2016-05-24 00:00:00 W3SVC1 GET /cheap/bridesmaid-dresses-104673/begin-blue-10151v10157-natural-10113v10115-sleeveless-10106v10107-end/ - - 157.55.39.59 HTTP/1.1 Mozilla/5.0+(compatible;+bingbot/2.0;++http://www.bing.com/bingbot.htm) - - www.ar1001.com 200 69462 2895
    */
    val conf=new SparkConf().setAppName("RDD编程")
    val sc=new SparkContext(conf)
    org.apache.spark.SparkContext
    //创建RDD
    val lines=sc.parallelize(List("pandas","I like pandas"))

    //读取文件创建RDD
    val fileLines=sc.textFile("/pvlog/AR1001/2016/05/20160524/u_ex16052400.log.gz")

    //转化操作
    val logLines=fileLines.filter(line=>line(0)!='#')

    println(s"该日志共有${logLines.count()}行")
    println("前10行：")
    logLines.take(10).foreach(println)
    logLines.map(line=>line.split(" "))
  }
}
