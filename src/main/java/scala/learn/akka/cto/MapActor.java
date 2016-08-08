package scala.learn.akka.cto;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * Created by Lei on 2016-8-8.
 */
public class MapActor  extends UntypedActor {
    public MapActor(ActorRef reductActor) {
        this.reductActor = reductActor;
    }

    private ActorRef reductActor;
    @Override
    public void onReceive(Object message) throws Exception {

    }
}
