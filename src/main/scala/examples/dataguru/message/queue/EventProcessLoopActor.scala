package examples.dataguru.message.queue

import akka.actor.{Actor, ActorSystem}
import akka.actor.Actor.Receive

/**
  * Created by Lei on 2016-8-7.
  */
class ProcessActor extends Actor{
  override def receive: Receive = {
    case msg:String=>println(msg)
  }
}

class  Master extends  Actor{
  override def receive: Receive = ???

  def main(args: Array[String]) {

  }
}