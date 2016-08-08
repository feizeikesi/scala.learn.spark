package examples.dataguru.message.queue

import akka.actor.{Actor, ActorRef, ActorSystem, Inbox, Props}
import scala.concurrent.duration._

case object  Greet
case class  WhoToGreet(who:String)
case class Greeting(message:String)

/**
  * Created by Lei on 2016-8-7.
  */
class Greeter extends  Actor{
  var greeting=""
  override def receive: Receive = {
    case WhoToGreet(who)=>greeting=s"hello,$who"
    case Greet => sender ! Greeting(greeting)
  }
}

object HelloAkkaScala extends  App{
  val system=ActorSystem("helloakka")

  val greeter=system.actorOf(Props[Greeter],"greeter")

  val inbox=Inbox.create(system)

  greeter.tell(WhoToGreet("akka"),ActorRef.noSender)

  inbox.send(greeter,Greet)

  val Greeting(msg)=inbox.receive(5.seconds)

  println(s"Greeting:$msg")

  greeter.tell(WhoToGreet("typesafe"),ActorRef.noSender)
  inbox.send(greeter,Greet)
  val Greeting(msg1)=inbox.receive(5.seconds)
  println(s"Greeting:$msg1")
  val greetPrinter = system.actorOf(Props[GreetPrinter])

  system.scheduler.schedule(0.seconds, 1.second, greeter, Greet)(system.dispatcher, greetPrinter)

}
class GreetPrinter extends Actor {
  def receive = {
    case Greeting(message) => println(message)
  }
}