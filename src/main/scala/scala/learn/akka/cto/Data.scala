package scala.learn.akka.cto

import scala.collection.immutable.HashMap

/**
  * Created by Lei on 2016-8-11.
  */
case class MapData(dataList: List[WordCount])  {
  def this
}

case class WordCount(word: String, count: Int)

case class RedcueData(reduceDataList: HashMap[String, Int])
