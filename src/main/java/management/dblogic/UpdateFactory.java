package management.dblogic;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateFactory extends StatementFactory {

    public UpdateFactory(String t, String[] f, String[] cond, String[] v) throws nullTableException {
        super(t, f, cond, v);
    }

    @Override
    public PreparedStatement buildStatement() throws SQLException {
        String stmt = "UPDATE " + table + " SET ";

        if(values == null) {
            throw new SQLException();
        }
        else {
            stmt += values[0];

            for(int i = 1; i < values.length; i++) {
                stmt += "," + values[i];
            }
        }

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
