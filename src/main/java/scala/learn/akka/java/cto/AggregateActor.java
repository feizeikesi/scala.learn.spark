package scala.learn.akka.java.cto;

import akka.actor.UntypedActor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lei on 2016-8-8.
 */
public class AggregateActor extends UntypedActor {
    private Map<String, Integer> finalReducedMap = new HashMap<>();

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ReduceData) {
            ReduceData reduceData = (ReduceData) message;
            aggregateInMemoryReduct(reduceData.getReduceDataList());
        } else if (message instanceof Result) {
            System.out.println(finalReducedMap.toString());
        } else
            unhandled(message);
    }

    private void aggregateInMemoryReduct(HashMap<String, Integer> reduceDataList) {
        Integer count = null;
        for (String key : reduceDataList.keySet()) {
            if (finalReducedMap.containsKey(key)) {
                count = reduceDataList.get(key) + finalReducedMap.get(key);
                finalReducedMap.put(key, count);
            } else {
                finalReducedMap.put(key, reduceDataList.get(key));
            }
        }
    }
}
