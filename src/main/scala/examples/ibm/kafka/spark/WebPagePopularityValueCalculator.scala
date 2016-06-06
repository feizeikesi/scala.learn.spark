package examples.ibm.kafka.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}
import org.apache.spark.{HashPartitioner, SparkConf}

/**
  * Created by lvshujing on 16/5/23.
  */
object WebPagePopularityValueCalculator {

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

    val msgDataRDD = kafkaStream.map(_._2)

    println("......................开始处理数据......................")
    msgDataRDD.print()


    msgDataRDD.saveAsTextFiles("hdfs://user/root/test.csv")
/*
    val popularityData = msgDataRDD.map ( msgLine => {
     // println("......................行数据......................")
     // println(msgLine)
      val dataArr: Array[String] =  msgLine.split("|")
   /*   val pageID = dataArr(0)
      val popValue: Double = dataArr(1).toDouble * 0.8 + dataArr(2).toDouble * 0.8 + dataArr(3).toDouble * 1
      (pageID, popValue)*/
    })

    popularityData.foreachRDD(rdd=>{
      println("......................行数据......................")
      rdd.foreach(println(_))
    })*/
/*
    val popularityData = msgDataRDD.map { msgLine => {
      println("......................行数据......................")
      println(msgLine)
      val dataArr: Array[String] = msgLine.split("|")
      val pageID = dataArr(0)
      val popValue: Double = dataArr(1).toFloat * 0.8 + dataArr(2).toFloat * 0.8 + dataArr(3).toFloat * 1
      (pageID, popValue)
    }
    }

    val updatePopularityValue = (iterator: Iterator[(String, Seq[Double], Option[Double])]) => {
      iterator.flatMap(t => {
        val newValue: Double = t._2.sum
        val stateValue: Double = t._3.getOrElse(0)
        Some(newValue + stateValue)
      }.map(sumedValue => (t._1, sumedValue)))
    }

    val initialRDD: RDD[(String, Double)] = ssc.sparkContext.parallelize[(String, Double)](List(("page1", 0.00)))

    val stateDstream = popularityData.updateStateByKey[Double](updatePopularityValue,
      new HashPartitioner(ssc.sparkContext.defaultParallelism), true, initialRDD)

    stateDstream.checkpoint(Duration(8*processsingInterval.toInt*1000))

    stateDstream.foreachRDD{rdd=>{
      val soredData=rdd.map{case (k,v)=>(v,k)}.sortByKey(false)
      val topKData=soredData.take(10).map{case(k,v)=>(v,k)}
      topKData.foreach(x=>{
        println(x)
      })
    }}*/

    ssc.start()
    ssc.awaitTermination()
  }
}
