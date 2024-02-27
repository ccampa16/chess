package service;

import dataAccess.AuthDAOMemory;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.GameDAOMemory;
import dataAccess.UserDAOMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClearServiceTest {
    private UserDAOMemory userDAOMemory;
    private AuthDAOMemory authDAOMemory;
    private GameDAOMemory gameDAOMemory;
    private ClearService clearService;

    @BeforeEach
    public void setUp(){
        userDAOMemory = new UserDAOMemory();
        authDAOMemory = new AuthDAOMemory();
        gameDAOMemory = new GameDAOMemory();
        clearService = new ClearService(userDAOMemory, authDAOMemory, gameDAOMemory);
    }
    @Test
    void clearTest() throws DataAccessException {
        userDAOMemory.createUser("user1", "pass1", "user1@gmail");
        userDAOMemory.createUser("user2", "pass2", "user2@gmail");
        authDAOMemory.createAuth("user1", "authtoken1");
        authDAOMemory.createAuth("user2", "authtoken2");

        clearService.clear();
        Assertions.assertNull(userDAOMemory.getUser("user1"));
        Assertions.assertNull(userDAOMemory.getUser("user2"));
        Assertions.assertNull(authDAOMemory.getAuth("auhtoken1"));
        Assertions.assertNull(authDAOMemory.getAuth("auhtoken2"));
    }
}