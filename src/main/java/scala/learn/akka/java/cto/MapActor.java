package scala.learn.akka.java.cto;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Lei on 2016-8-8.
 */
public class MapActor extends UntypedActor {

    private String[] STOP_WORDS = {"a", "is"};
    private List<String> STOP_WORDS_LIST = new ArrayList<String>();
    private ActorRef reductActor;

    public MapActor(ActorRef reductActor) {
        this.reductActor = reductActor;
        for (String work : STOP_WORDS) {
            STOP_WORDS_LIST.add(work);
        }
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            String work = (String) message;

            scala.learn.akka.cto.MapData data = evaluateExpression(work);

            reductActor.tell(data, null);
        } else {
            unhandled(message);
        }
    }

    private scala.learn.akka.cto.MapData evaluateExpression(String line) {
        List<WordCount> dataList = new ArrayList<>();
        StringTokenizer parser = new StringTokenizer(line);
        while (parser.hasMoreTokens()) {
            String word = parser.nextToken().toLowerCase();
            if (!STOP_WORDS_LIST.contains(word)) {
                dataList.add(new WordCount(word, Integer.valueOf(1)));
            }
        }
        return new scala.learn.akka.java.cto.MapData(dataList);
    }
}
