package dataAccessTests;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.SQL.SQLUserDAO;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDAOTests {
    private SQLUserDAO userDAO;
    @BeforeEach
    public void setUp() throws DataAccessException{
        userDAO = new SQLUserDAO();
        userDAO.clear();
    }
    @Test
    public void createUserSuccess() throws DataAccessException {
        UserData user = userDAO.createUser("testuser", "pass", "gmail");
        UserData retrieveUser = userDAO.getUser("testuser");
        Assertions.assertEquals(user.username(), retrieveUser.username());
    }
    @Test
    public void createUserFail() throws DataAccessException {
        userDAO.createUser("test", "pass", "gmail");
        Assertions.assertThrows(DataAccessException.class, () -> userDAO.createUser("test", "anotherpass", "yahoo"));
    }
    @Test
    public void getUserSuccess() throws DataAccessException {
        UserData user = userDAO.createUser("test", "pass", "gmail");
        UserData retrieveUser = userDAO.getUser("test");
        Assertions.assertEquals(user.username(), retrieveUser.username());
    }
    @Test
    public void getUserFail() throws DataAccessException {
        UserData retrieveUser = userDAO.getUser("notUser");
        Assertions.assertNull(retrieveUser);
    }
    @Test
    public void checkUserSuccess() throws DataAccessException {
        userDAO.createUser("test", "pass", "gmail");
        boolean userExists = userDAO.checkUser("test", "pass");
        Assertions.assertTrue(userExists);
    }
    @Test
    public void checkUserFail() throws DataAccessException {
        userDAO.createUser("test", "pass", "gmail");
        boolean userExists = userDAO.checkUser("test", "wrong");
        Assertions.assertFalse(userExists);
    }
    @Test
    public void clearSuccess() throws DataAccessException{
        userDAO.createUser("test", "pass", "gmail");
        userDAO.clear();
        UserData retrieve = userDAO.getUser("test");
        Assertions.assertNull(retrieve);
    }
}
