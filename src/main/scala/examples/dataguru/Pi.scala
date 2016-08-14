package examples.dataguru

import  akka.util


/**
  * Created by Administrator on 2016/8/5.
  */
class Pi {

}

sealed trait PiMessage
case  object Calculate extends PiMessage
case class  Work(start:Int,nrOfElements:Int) extends PiMessage
case class  Result(value:Double) extends PiMessage
//case class PiApproximation(pi:Double,duration: Duration)
