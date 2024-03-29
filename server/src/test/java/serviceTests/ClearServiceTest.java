package serviceTests;

import dataAccess.Memory.AuthDAOMemory;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Memory.GameDAOMemory;
import dataAccess.Memory.UserDAOMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

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
    @Test
    void clearFail() throws DataAccessException{
        userDAOMemory.createUser("user1", "pass1", "user1@gmail");
        userDAOMemory.createUser("user2", "pass2", "user2@gmail");
        authDAOMemory.createAuth("user1", "authtoken1");
        authDAOMemory.createAuth("user2", "authtoken2");
        clearService.clear();
        userDAOMemory.createUser("user3", "pass3", "user3@gmail");
        authDAOMemory.createAuth("user3", "authtoken3");
        Assertions.assertNotNull(userDAOMemory.getUser("user3"));
        Assertions.assertNotNull(authDAOMemory.getAuth("authtoken3"));
    }
}