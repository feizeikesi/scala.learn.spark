package scala.learn.akka.cto

import akka.actor.Actor.Receive
import akka.actor.{Actor, UntypedActor}

/**
  * Created by Lei on 2016-8-11.
  */
class MapActor extends UntypedActor{

  override def onReceive(message: Any): Unit = {
    message match {
      case ms:String=>_
      case _=>unhandled(message)
    }
  }
}
