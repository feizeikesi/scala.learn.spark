package examples.dataguru.MLlib

/**
  * Created by Lei on 2016-10-16.
  */
object Demo {
  def main(args: Array[String]) {
    val sqlContext = new org.apache.spark.sql.hive.HiveContext(sc)
    sqlContext.sql("SET spark.sql.shuffle.partitions=20")
    val sqldata = sqlContext.sql("select locationid, sum(num) allnum,sum(amount) allamount from sell group by locationid ")
    sqldata.collect().foreach(println)
    val parsedData = sqldata.map {
      case Row(_, allnum, allamount) =>
        val features = Array[Double](allnum.toString.toDouble, allamount.toString.toDouble)
        Vectors.dense(features)
    }
    parsedData.collect().foreach(println)
    val numClusters = 3
    val numIterations = 20
    val model = KMeans.train(parsedData, numClusters, numIterations)
    val result1 = sqldata.map {
      case Row(locationid, allnum, allamount) =>
        val features = Array[Double](allnum.toString.toDouble, allamount.toString.toDouble)
        val linevectore = Vectors.dense(features)
        val prediction = model.predict(linevectore)
        locationid + " " + allnum + " " + allamount + " " + prediction
    }.collect().foreach(println)
    //保存文件
    val result2 = sqldata.map {
      case Row(locationid, allnum , allamount) =>
        val features = Array[Double](allnum.toString.toDouble, allamount.toString.toDouble)
        val linevectore = Vectors.dense(features)
        val prediction = model.predict(linevectore)
        locationid + " " + allnum + " " + allamount + " " + prediction
    }.saveAsTextFile("/tmp/test")
  }
}
