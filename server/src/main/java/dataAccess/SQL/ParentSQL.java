package dataAccess.SQL;

import dataAccess.DatabaseManager;
import dataAccess.Exceptions.DataAccessException;

import java.sql.Connection;
import java.sql.SQLException;

public class ParentSQL {
    protected void createDatabase(String[] statements) throws DataAccessException{ //protected or private??
        DatabaseManager.createDatabase();
        try (Connection connection = DatabaseManager.getConnection()){
            for (String stmt : statements) {
                try (var preparedStatement = connection.prepareStatement(stmt)) {
                    preparedStatement.executeUpdate();
                }
            }
    } catch (Exception e){
            throw new DataAccessException(String.format("Unable to configure database: %s", e.getMessage()));
        }
    }
}
