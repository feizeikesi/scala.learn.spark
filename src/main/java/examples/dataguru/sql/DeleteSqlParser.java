package examples.dataguru.sql;

/**
 * Created by Lei on 2016-9-18.
 */
public class DeleteSqlParser extends BaseSingleSqlParser {
    public DeleteSqlParser(String originalSql) {
        super(originalSql);
    }

    @Override
    protected void initializeSegments() {
        segments.add(new SqlSegment("(delete from)(.+)( where | ENDOFSQL)", "[,]"));
        segments.add(new SqlSegment("(where)(.+)( ENDOFSQL)", "(and|or)"));
    }
}