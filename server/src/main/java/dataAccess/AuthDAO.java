package dataAccess;
import model.AuthData;
public interface AuthDAO{
    void createAuth(String username, String authtoken) throws DataAccessException; // was just username
    AuthData getAuth(String authToken) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
    void clear() throws DataAccessException;
}
