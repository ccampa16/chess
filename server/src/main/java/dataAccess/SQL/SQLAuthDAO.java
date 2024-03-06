package dataAccess.SQL;

import dataAccess.AuthDAO;
import dataAccess.Exceptions.DataAccessException;
import model.AuthData;

public class SQLAuthDAO extends ParentSQL implements AuthDAO {
    public SQLAuthDAO() throws DataAccessException {
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

    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }

    @Override
    public boolean checkAuth(String authtoken) throws DataAccessException {
        return false;
    }

    @Override
    public void clear() throws DataAccessException {

    }
}
