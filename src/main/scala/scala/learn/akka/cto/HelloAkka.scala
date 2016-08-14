package scala.learn.akka.cto

import akka.actor.{ActorSystem, Props}



/**
  * Created by Lei on 2016-8-11.
  */
object HelloAkka {
  def main(args: Array[String]) {
      val system=ActorSystem("")
     system.actorOf(Props[MapActor])

  }
}
