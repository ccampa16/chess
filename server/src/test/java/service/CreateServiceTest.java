package service;

import dataAccess.Memory.AuthDAOMemory;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
import dataAccess.Memory.GameDAOMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import result.CreateGameResult;
import spark.Request;

public class CreateServiceTest {
    private CreateGameService createGameService;
    private AuthDAOMemory authDAOMemory;
    private GameDAOMemory gameDAOMemory;
    private Request requestMock;
    @BeforeEach
    public void setUp(){
        AuthDAOMemory authDAOMemory = new AuthDAOMemory();
        authDAOMemory.createAuth("valid_username", "valid_authtoken");
        GameDAOMemory gameDAOMemory = new GameDAOMemory();
        createGameService = new CreateGameService(authDAOMemory, gameDAOMemory);
    }
    @Test
    void createGameUnauthorized() throws DataAccessException{
        CreateGameRequest request = new CreateGameRequest("GameName");
        Assertions.assertThrows(UnauthorizedException.class, ()-> createGameService.createGame(request, "invalid_authtoken"));
    }
    @Test
    void createGameSuccess() throws DataAccessException{
        CreateGameRequest request = new CreateGameRequest("GameName");
        //String authtoken = UUID.randomUUID().toString();
        CreateGameResult result = createGameService.createGame(request, "valid_authtoken");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getGameID() > 0);
    }

}
