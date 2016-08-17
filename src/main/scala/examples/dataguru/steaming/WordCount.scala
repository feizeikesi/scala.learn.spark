package examples.dataguru.steaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Lei on 2016-8-17.
  */
object WordCount {
  def main(args: Array[String]) {
    val conf=new SparkConf().setAppName("Word Count")
    val ssc=new StreamingContext(conf,Seconds(1))
    val lines =ssc.socketTextStream("master.hadoop.tlz",9999)
     lines.map( line =>if (line.split(" ").length>0) Some(line) else None)
      .filter(_.isDefined)
        .saveAsTextFiles("msg")

    ssc.start()
    ssc.awaitTermination()

  }
}
