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

    paris.collect() //Array((1,2), (3,4), (3,6))

    //合并具有相同键的值
    paris.reduceByKey((x,y)=>x+y).collect()//Array((1,2), (3,10))

    //对具有相同键的值进行分组
    paris.groupByKey().collect()//Array((1,CompactBuffer(2)), (3,CompactBuffer(4, 6)))

    //对 pari RDD中的每个值应用一个函数而不改变键
    paris.mapValues(x=>x+1).collect()//Array((1,3), (3,5), (3,7))

    //对 pari RDD 中每个值应用一个返回迭代器的函数，然后对返回的每个元素都生成一个对应原键的键值对记录，通常用于符号化
    paris.flatMapValues(x=>(x to 5)).collect()//Array((1,2), (1,3), (1,4), (1,5), (3,4), (3,5))

    //返回一个仅包含键的RDD
    paris.keys.collect()// Array(1, 3, 3)

    //返回一个仅包含值的RDD
    paris.values.collect()// Array(2, 4, 6)

    //返回一个根据键排序的RDD
    paris.sortByKey().collect()//Array((1,2), (3,4), (3,6))

  }
}
