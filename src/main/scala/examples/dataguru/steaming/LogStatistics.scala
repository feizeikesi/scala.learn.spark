package examples.dataguru.steaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Administrator on 2016/9/2.
  */
object LogStatistics {
  private val checkpointDir = "popularity-data-checkpoint"
  private val msgConsumerGroup = "user-behavior-topic-message-consumer-group"
  def main(args: Array[String]) {

    val processsingInterval = 2
    val zkServers = "slave1.kafka.tlz:2181,slave2.kafka.tlz:2181,slave3.kafka.tlz:2181"
    val conf = new SparkConf().setAppName("Web Page Popularity Value Calculator")
    val ssc = new StreamingContext(conf, Seconds(processsingInterval.toInt))
    ssc.checkpoint(checkpointDir)
    val kafkaStream = KafkaUtils.createStream(
      //Spark streaming context
      ssc,
      //zookeeper quorum,e.g zkserver1:2181,zkserver2:2181
      zkServers,
      //
      msgConsumerGroup,
      Map("user-behavior-topic" -> 3)
    )

    //kafkaStream.map()

    ssc.start()
    ssc.awaitTermination()
  }
}
