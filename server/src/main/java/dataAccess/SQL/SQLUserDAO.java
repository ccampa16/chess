package dataAccess.SQL;

import dataAccess.DatabaseManager;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.UserDAO;
import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserDAO extends ParentSQL implements UserDAO {
    private SQLUserDAO() throws DataAccessException { //private or public
        String[] statements = {
            """
            CREATE TABLE IF NOT EXISTS user (
            'username' varchar(200) NOT NULL,
            'password' varchar(200) NOT NULL,
            'email' varchar(200) NOT NULL,
            PRIMARY KEY ('username')
            )
           """
        };
        createDatabase(statements);
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()){
            String statement = "SELECT * FROM user WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()){
                        return new UserData(
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("email")
                        );
                    } else {
                        return null;
                    }
                }

            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public UserData createUser(String username, String password, String email) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()){
            String stmt = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(stmt, PreparedStatement.RETURN_GENERATED_KEYS)){
                ps.setString(1, username);
                ps.setString(2, username);
                ps.setString(3, email);
                ps.executeUpdate();
                return new UserData(username, password, email);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public boolean checkUser(String username, String password) throws DataAccessException {
        try(Connection conn = DatabaseManager.getConnection()){
            String stmt = "SELECT * FROM user WHERE username = ? AND password = ?";
            try (PreparedStatement ps = conn.prepareStatement(stmt)){
                ps.setString(1, username);
                ps.setString(2, password);
                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()){
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return false;
    }

    @Override
    public void clear() throws DataAccessException {
        try(Connection conn = DatabaseManager.getConnection()){
            String stmt = "DELETE FROM user";
            try (PreparedStatement ps = conn.prepareStatement(stmt)){
                ps.executeUpdate();
            }
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }
}
