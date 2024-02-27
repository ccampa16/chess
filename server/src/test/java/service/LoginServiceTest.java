package service;

import dataAccess.AuthDAOMemory;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.UserDAOMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import result.LoginResult;

public class LoginServiceTest {
    private LoginService loginService;
    @BeforeEach
    public void setUp(){
        UserDAOMemory userDAOMemory = new UserDAOMemory();
        AuthDAOMemory authDAOMemory = new AuthDAOMemory();
        userDAOMemory.createUser("testUser", "testPassword", "test@gmail");
        userDAOMemory.createUser("anotherUser", "anotherPassword", "another@gmail");
        loginService = new LoginService(userDAOMemory, authDAOMemory);
    }
    @Test
    public void testSuccessfulLogin() throws DataAccessException{
        LoginRequest request = new LoginRequest("testUser", "testPassword");
        LoginResult loginResult = loginService.login("testUser", "testPassword");
        Assertions.assertEquals("testUser", request.getUsername());
        Assertions.assertEquals(LoginResult.class, loginResult.getClass());
        Assertions.assertNull(loginResult.getMessage());
        Assertions.assertNotNull(loginResult.getUsername());
        Assertions.assertNotNull(loginResult.getAuthToken());
    }
    @Test
    public void testLoginAfterRegister() throws DataAccessException{
        LoginResult loginResult = loginService.login("testUser", "testPassword");
        Assertions.assertNotNull(loginResult);
        Assertions.assertNull(loginResult.getMessage());
        Assertions.assertEquals("testUser", loginResult.getUsername());
        Assertions.assertNotNull(loginResult.getAuthToken());

    }
}
