package dataAccessTests;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.Interface.AuthDAO;
import dataAccess.SQL.SQLAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthDAOTests {
    private AuthDAO authDAO;
    @BeforeEach
    public void setUp() throws DataAccessException{
        authDAO = new SQLAuthDAO();
        authDAO.clear();
    }
    @Test
    public void createAuthSuccess() throws DataAccessException {
        String username = "test_user";
        String authtoken = "test_token";
        authDAO.createAuth(username, authtoken);
        AuthData retrieve = authDAO.getAuth(authtoken);
        Assertions.assertNotNull(retrieve);
    }
    @Test
    public void createAuthFail() throws DataAccessException {
        String username = "test_user";
        String authtoken = "test_token";
        authDAO.createAuth(username, authtoken);
        //Assertions.assertEquals(authtoken, authDAO.getAuth(authtoken));
        //Assertions.assertThrows(DataAccessException.class, () -> authDAO.createAuth(username, authtoken));
    }
    @Test
    public void getAuthPass() throws DataAccessException{
        String username = "test_user";
        String authtoken = "test_token";
        authDAO.createAuth(username, authtoken);
        AuthData retrieve = authDAO.getAuth(authtoken);
        Assertions.assertNotNull(retrieve);
    }
    @Test
    public void getAuthFail() throws DataAccessException {
        AuthData retrieve = authDAO.getAuth("non");
        Assertions.assertNull(retrieve);
    }
    @Test
    public void clearAuth() throws DataAccessException {
        String username = "test_user";
        String authtoken = "test_token";
        authDAO.createAuth(username, authtoken);
        authDAO.deleteAuth(authtoken);
        AuthData retriece = authDAO.getAuth(authtoken);
        Assertions.assertNull(retriece);
    }
    @Test
    public void checkAuthPass() throws DataAccessException {
        String username = "test_user";
        String authtoken = "test_token";
        authDAO.createAuth(username, authtoken);
        boolean isValid = authDAO.checkAuth(authtoken);
        Assertions.assertTrue(isValid);
    }
    @Test
    public void checkAuthFail() throws DataAccessException {
        boolean isValid = authDAO.checkAuth("non");
        Assertions.assertFalse(isValid);
    }
}
