package scala.learn.akka.java.cto;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

/**
 * Created by Lei on 2016-8-8.
 */
public class MasterActor extends UntypedActor {

    private ActorRef aggregateActor = getContext().actorOf(Props.create(AggregateActor.class), "aggreagate");

    private ActorRef reductActor = getContext().actorOf(Props.create(ReduceActor.class, aggregateActor), "reduce");

    private ActorRef mapActor = getContext().actorOf(Props.create(MapActor.class, new Creator<MapActor>() {
        @Override
        public MapActor create() throws Exception {
            return new MapActor(reductActor);
        }
    }));

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            mapActor.tell(message, null);
        } else if (message instanceof Result) {
            aggregateActor.tell(message, null);
        } else
            unhandled(message);
    }
}
