package dataAccess.SQL;

import dataAccess.Interface.AuthDAO;
import dataAccess.DatabaseManager;
import dataAccess.Exceptions.DataAccessException;
import model.AuthData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLAuthDAO extends ParentSQL implements AuthDAO {
    public SQLAuthDAO() throws DataAccessException {
        super();
        String[] statements = { //what does auto increment do?? saw it on pet shop
                """
            CREATE TABLE IF NOT EXISTS auth (
            'id' int NOT NULL AUTO_INCREMENT,
            'authtoken' varchar(200) NOT NULL,
            'username' varchar(200) NOT NULL,
            PRIMARY KEY ('id')
            )
           """
        };
        createDatabase(statements);
    }

    @Override
    public void createAuth(String username, String authtoken) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()){
            String stmt = "INSERT INTO auth (authtoken, username) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(stmt)){
                ps.setString(1, authtoken);
                ps.setString(2, username);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()){
            String stmt = "SELECT * FROM auth WHERE authtoken = ?";
            try (PreparedStatement ps = conn.prepareStatement(stmt)){
                ps.setString(1, authToken);
                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()){
                        return new AuthData(
                                rs.getString("authtoken"),
                                rs.getString("username")
                        );
                    }
                }
            }
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()){
            String stmt = "DELETE FROM auth WHERE authtoken = ?";
            try (PreparedStatement ps = conn.prepareStatement(stmt)){
                ps.setString(1, authToken);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public boolean checkAuth(String authtoken) throws DataAccessException {
        return getAuth(authtoken) != null;
    }

    @Override
    public void clear() throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()){
            String stmt = "DELETE FROM auth";
            try (PreparedStatement ps = conn.prepareStatement(stmt)){
                ps.executeUpdate();
            }
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }
}
