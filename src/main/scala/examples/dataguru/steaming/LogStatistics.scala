package examples.dataguru.steaming

<<<<<<< HEAD
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

=======
import examples.dataguru.steaming.Geo.MaxMindIpGeo
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkFiles}

/**
  * Created by Administrator on 2016/9/2.
  */
object LogStatistics {
  private val checkpointDir = "popularity-data-checkpoint"
  private val msgConsumerGroup = "user-behavior-topic-message-consumer-group"
  private final val GEOLITE2_CITY_NAME: String = "GeoLite2-City.mmdb"

  def main(args: Array[String]) {

    val processsingInterval = 1
    val zkServers = "slave1.kafka.tlz:2181,slave2.kafka.tlz:2181,slave3.kafka.tlz:2181"
    val conf = new SparkConf().setAppName("Web Page Popularity Value Calculator")
    val ssc = new StreamingContext(conf, Seconds(processsingInterval.toInt))
    ssc.sparkContext.addFile(GEOLITE2_CITY_NAME)
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

   val  msgs =kafkaStream.map(a=>TraceMessage(a._2)).filter(_.isDefined)

    val modelCount=msgs.map(_.get.modelName->1).reduceByKey(_+_).cache()

    //统计模块pv
    modelCount.saveAsTextFiles("model-count")

    //统计最受欢迎的模块
    modelCount.transform(rdd=>ssc.sparkContext.parallelize(rdd.sortByKey().take(10)))
        .saveAsTextFiles("model-top")

    //统计地区分布
    msgs.map(_.get.userIp->1).reduceByKey(_+_).repartition(10)
      .mapPartitions(par=>{
        val geo = MaxMindIpGeo(SparkFiles.get(GEOLITE2_CITY_NAME),
          synchronized = true)
        par.map(ip=>{
          try {
            geo.getLocation(ip._1) match {
              case Some(ipLocation)
                if ipLocation.countryName.isDefined =>
                ipLocation.countryName -> ip._2
              case _ => "未知"->ip._2
            }
          } catch {
            case _: Throwable => "未知"->ip._2
          }
        })
      }).reduceByKey(_+_)
        .saveAsTextFiles("ip-stat")

    ssc.start()
    ssc.awaitTermination()
>>>>>>> 59e5cf040940bd6abf61ee01aca16ad0e3e6bd9b
  }
}
