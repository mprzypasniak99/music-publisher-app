package management.dblogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class StatementFactory {
    protected String table;
    protected String[] fields;
    protected String[] conditions;
    protected String[] values;
    protected Connection connection;

    public void setConnection(Connection conn) {
        connection = conn;
    }

    public class nullTableException extends Exception {

    }

    public abstract PreparedStatement buildStatement() throws SQLException;

    public StatementFactory(String t, String[] f, String[] cond, String[] v) throws nullTableException {
        if(t == null) {
            throw new nullTableException();
        }
        table = t;
        fields = f;
        conditions = cond;
        values = v;
    }
}
