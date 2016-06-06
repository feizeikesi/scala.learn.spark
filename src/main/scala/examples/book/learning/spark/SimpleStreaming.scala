package examples.book.learning.spark

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{ Seconds, StreamingContext }

/**
  * Created by lvshujing on 16/5/26.
  */
object SimpleStreaming {
  def main(args: Array[String]) {
    val conf=new SparkConf().setAppName("ScoketStream")
    //从SparkConf创建SreamingContext并指定1秒钟为批处理大小
    val ssc=new StreamingContext(conf,Seconds(1))

    //连接到本机的7777端口,使用接受到的数据创建DStream
    val lines =ssc.socketTextStream("master.hadoop.tlz",9999)

    lines.saveAsTextFiles("hdfs://master.hadoop.tlz:8020/tmp/test")

    val errorLines=lines.filter(_.contains("error"))

    errorLines.print()



    //errorLines.saveAsHadoop
    sys.ShutdownHookThread{
      ssc.stop(true,true)
    }
    ssc.start()
    ssc.awaitTermination()

  }
}
