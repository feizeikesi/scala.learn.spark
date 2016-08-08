package scala.learn.akka.cto;

import java.util.List;

/**
 * Created by Lei on 2016-8-8.
 */

//
public class MapData {
    private List<WordCount> dataList;

    public List<WordCount> getDataList() {
        return dataList;
    }

    public MapData(List<WordCount> dataList) {
        this.dataList = dataList;
    }
}

