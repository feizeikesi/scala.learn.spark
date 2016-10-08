package examples.dataguru.sql;

/**
 * Created by Lei on 2016-9-18.
 */
public class NoSqlParserException extends Exception {
    private static final long serialVersionUID = 1L;

    NoSqlParserException() {

    }

    NoSqlParserException(String sql) {
        //调用父类方法
        super(sql);
    }
}

