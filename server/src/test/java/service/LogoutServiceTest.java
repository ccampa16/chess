package service;

import dataAccess.AuthDAOMemory;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LogoutServiceTest {
    private LogoutService logoutService;
    private AuthDAOMemory authDAOMemory;
    @BeforeEach
    public void setUp(){
        authDAOMemory = new AuthDAOMemory();
        logoutService = new LogoutService(authDAOMemory);
    }
    @Test
    public void logoutSuccess() throws DataAccessException{
        String authToken = "validToken";
        authDAOMemory.createAuth("username", authToken);
        Assertions.assertDoesNotThrow(() -> logoutService.logout(authToken));
        Assertions.assertFalse(authDAOMemory.checkAuth(authToken));
    }
    @Test
    public void logoutFail() throws DataAccessException{
        String authToken = "invalidAuthtoken";
        UnauthorizedException exception = Assertions.assertThrows(UnauthorizedException.class,
                () -> logoutService.logout(authToken));
        Assertions.assertEquals("unauthorized", exception.getMessage());

    }
}
