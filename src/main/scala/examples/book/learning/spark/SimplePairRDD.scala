package examples.book.learning.spark

import org.apache.spark.{ SparkConf, SparkContext }

/**
  * Created by Lei on 2016-6-6.
  */
object SimplePairRDD {
  def main (args : Array[String]) {
    val conf=new SparkConf().setAppName("")
    val sc=new SparkContext(conf)
    val lines =sc.parallelize(List("1 2","3 4","3 6"))
    //创建pair RDD
    val paris=lines.map(line=>{
      val values=line.split(" ")
      (values(0).toInt,values(1).toInt)
    })

    paris.collect().foreach(println(_))  //((1,2),(3,4)(3,6))

    //合并具有相同键的值
    paris.reduceByKey((x,y)=>x+y).collect().foreach(println(_)) //{(1,2),(3,10)}

  }
}
