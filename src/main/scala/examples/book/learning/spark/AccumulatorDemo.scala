package examples.book.learning.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2016/7/2.
  */
//累加器
object AccumulatorDemo {
  def main(args: Array[String]) {
    val cf=new SparkConf().setAppName("")
    val sc=new SparkContext(cf)

    val blankLines=sc.accumulable[Int,Int](0)

    val file=sc.textFile("")
    val callSigns=file.flatMap(line=>{
       if(line==""){
         blankLines += 1
       }

      line.split(" ")
    })

    callSigns.saveAsObjectFile("")

    println("Blank lines: "+blankLines.value)
  }
}
