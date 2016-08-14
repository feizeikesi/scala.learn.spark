package scala.learn.akka.java.cto;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Lei on 2016-8-8.
 */
public class ReduceActor extends UntypedActor {
    private ActorRef aggregateActor = null;

    public ReduceActor(ActorRef aggregateActor) {
        this.aggregateActor = aggregateActor;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof scala.learn.akka.cto.MapData) {
            scala.learn.akka.cto.MapData mapData = (scala.learn.akka.cto.MapData) message;
            ReduceData reduceData = reduce(mapData.getDataList());
            aggregateActor.tell(reduceData,null);
        }else
            unhandled(message);
    }

    private ReduceData reduce(List<WordCount> dataList) {
        HashMap<String, Integer> reduceMap = new HashMap<>();
        for (WordCount wordCount : dataList) {
            if (reduceMap.containsKey(wordCount.getWord())) {
                Integer value = reduceMap.get(wordCount.getWord());
                value++;
                reduceMap.put(wordCount.getWord(), value);
            } else {
                reduceMap.put(wordCount.getWord(), Integer.valueOf(1));
            }
        }
        return new ReduceData(reduceMap);
    }
}
