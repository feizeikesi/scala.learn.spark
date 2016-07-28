package examples.dataguru

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2016/7/28.
  */
object SecondWeekPractice {
  def main(args: Array[String]) {
      val conf=new SparkConf().setAppName("SecondWeekPractice")
      val sc=new SparkContext(conf)
      val lines= sc.textFile("/tmp/users.txt")


      val phoneAndRegion=lines.map(line=>{
        val cols=line.split(",")
        if(cols.length==8)
          Some((cols(2),cols(3)))
        else
          None
      }).filter(_.isDefined).map(_.get).persist()

    val region=phoneAndRegion.map(line=>(line._2,1)).reduceByKey(_+_)
    val phone=phoneAndRegion.map(line=>(line._1.substring(0,3),1)).reduceByKey(_+_).sortByKey()
    region.union(phone).coalesce(1,true).saveAsTextFile("/tmp/result")
  }
}
