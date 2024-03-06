package dataAccess.SQL;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.UserDAO;
import model.UserData;

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
        return null;
    }

    @Override
    public UserData createUser(String username, String password, String email) throws DataAccessException {
        return null;
    }

    @Override
    public boolean checkUser(String username, String password) throws DataAccessException {
        return false;
    }

    @Override
    public void clear() throws DataAccessException {

    }
}
