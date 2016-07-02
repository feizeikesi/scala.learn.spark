package examples.book.learning.spark

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

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

    //针对两个pair RDD 的转化操作
    val other=sc.parallelize(List((3,9)))

    paris.subtract(other).collect()

    //对两个RDD进行内链接
    paris.join(other).collect()//Array((3,(4,9)), (3,(6,9)))

    //对两个RDD进行连接操作，确保第二个RDD的键必须存在
    paris.rightOuterJoin(other).collect()//Array((3,(Some(4),9)), (3,(Some(6),9)))

    //对两个RDD进行连接操作，确保第一个RDD的键必须存在
    paris.leftOuterJoin(other).collect()// Array((1,(2,None)), (3,(4,Some(9))), (3,(6,Some(9))))

    //将两个RDD中拥有相同键的数据分组到一起
    paris.cogroup(other).collect()// Array((1,(CompactBuffer(2),CompactBuffer())), (3,(CompactBuffer(4, 6),CompactBuffer(9))))


    //统计数字个数
    paris.flatMap(x=>List(x._1,x._2))
      .map(x=>(x,1)).reduceByKey((x,y)=>x+y).collect()// Array((4,1), (6,1), (2,1), (1,1), (3,2))

    //统计每个key的平均值
    paris.mapValues(x=>(x,1)).
      reduceByKey((x,y)=>(x._1+y._1,x._2+y._2),1).
      map(x=>(x._1,x._2._1/x._2._2)).collect() //Array((1,2), (3,5))

    paris.combineByKey(
      (v)=>(v,1),
      (acc:(Int,Int),v)=>(acc._1+v,acc._2+1),
      (acc1:(Int,Int),acc2:(Int,Int))=>(acc1._1+acc2._1,acc1._2+acc2._2)
    ).map(x=>(x._1,x._2._1/x._2._2)).collect()//Array((1,2), (3,5))

    //Join
    paris.join(other).collect()//Array((3,(4,9)), (3,(6,9)))

    //左连接
    paris.leftOuterJoin(other).collect()//Array((1,(2,None)), (3,(4,Some(9))), (3,(6,Some(9))))

    //右连接
    paris.rightOuterJoin(other).collect()// Array((3,(Some(4),9)), (3,(Some(6),9)))

    //获取RDD分区
    paris.partitioner

    val partitioned=paris.partitionBy(new HashPartitioner(2))

    paris.partitioner
  }
}
