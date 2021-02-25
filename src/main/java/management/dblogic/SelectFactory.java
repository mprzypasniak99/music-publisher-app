package management.dblogic;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SelectFactory extends StatementFactory {

    public SelectFactory(String t, String[] f, String[] cond, String[] v) throws nullTableException {
        super(t, f, cond, v);
    }

    @Override
    public PreparedStatement buildStatement() throws SQLException {
        String stmt = "SELECT ";

        // which fields to select - if null, then select all fields
        if(fields == null) {
            stmt += "* FROM ";
        }
        else {
            stmt += fields[0];

            for (int i = 1; i < fields.length; i++) {
                stmt += "," + fields[i];
            }

            stmt += " FROM ";
        }

        stmt += table;

        // WHERE statement

        if(conditions != null) {
            stmt += " WHERE " + conditions[0];

            for(int i = 1; i < conditions.length; i++) {
                stmt += " AND " + conditions[i];
            }
        }

        return connection.prepareStatement(stmt);
    }
}
