package scala.learn.akka.java.cto;

import java.util.HashMap;

/**
 * Created by Lei on 2016-8-8.
 */
public class ReduceData {
    private HashMap<String,Integer> reduceDataList;

    public HashMap<String, Integer> getReduceDataList() {
        return reduceDataList;
    }

    public ReduceData(HashMap<String, Integer> reduceDataList) {

        this.reduceDataList = reduceDataList;
    }
}
