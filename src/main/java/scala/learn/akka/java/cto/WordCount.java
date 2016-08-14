package scala.learn.akka.java.cto;

/**
 * Created by Lei on 2016-8-8.
 */

/**
 * 统计单词次数
 */
public class WordCount {
    private String word;
    private Integer count;

    public WordCount(String word, Integer count) {
        this.word = word;
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public String getWord() {

        return word;
    }
}
