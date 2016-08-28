package examples.dataguru.steaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Lei on 2016-8-28.
  */
object LogStatistics {
  def main(args: Array[String]) {
    val conf=new SparkConf()
    conf.setAppName("LogStatistics")

    val ssc=new StreamingContext(conf,Seconds(1))
    ssc.checkpoint("LogStatistics")

    val msgs =ssc.socketTextStream("master.hadoop.tlz",9999)
    val lines= msgs.map(IISLog.toIISLog(_)).filter(_.isDefined).map(_.get)

    //统计ip
    val  topIp=lines.map(_.c_ip->1).reduceByKeyAndWindow((_+_),(_-_),Seconds(30),Seconds(30))
      .transform(ips=>ips.sortBy(_._2,false).zipWithIndex().filter(_._2<50).map(_._1))


    //统计页面
    val  topUrl=lines.map(_.cs_uri_stem->1).reduceByKeyAndWindow((_+_),(_-_),Seconds(30),Seconds(30))
      .transform(ips=>ips.sortBy(_._2,false).zipWithIndex().filter(_._2<50).map(_._1))

  }
}
