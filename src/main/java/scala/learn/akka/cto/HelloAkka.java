package scala.learn.akka.cto;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by Lei on 2016-8-8.
 */
public class HelloAkka {

    public static void main(String[] args) throws InterruptedException {
        ActorSystem _system=ActorSystem.create("HelloAkka");
        ActorRef master=_system.actorOf(Props.create(MasterActor.class),"master");

        master.tell("Hi! My name is Rock. I'm so so so happy to be here.",null);

        Thread.sleep(500);

        master.tell(new Result(),null);

        Thread.sleep(500);

        _system.shutdown();
    }
}
