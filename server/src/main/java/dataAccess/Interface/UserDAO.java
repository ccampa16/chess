package dataAccess.Interface;
import dataAccess.Exceptions.DataAccessException;
import model.UserData;

public interface UserDAO {
    UserData getUser(String username) throws DataAccessException;
    UserData createUser(String username, String password, String email) throws DataAccessException;
    boolean checkUser(String username, String password) throws DataAccessException;
    void clear() throws DataAccessException;

}
