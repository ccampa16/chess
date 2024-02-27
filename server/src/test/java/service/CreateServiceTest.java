package service;

import dataAccess.AuthDAOMemory;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
import dataAccess.GameDAOMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import spark.Request;

public class CreateServiceTest {
    private CreateGameService createGameService;
    private AuthDAOMemory authDAOMemory;
    private GameDAOMemory gameDAOMemory;
    private Request requestMock;
    @BeforeEach
    public void setUp(){
        AuthDAOMemory authDAOMemory = new AuthDAOMemory();
        GameDAOMemory gameDAOMemory = new GameDAOMemory();
        createGameService = new CreateGameService(authDAOMemory, gameDAOMemory);
    }
    @Test
    void createGameUnauthorized() throws DataAccessException{
        CreateGameRequest request = new CreateGameRequest("GameName");
        Assertions.assertThrows(UnauthorizedException.class, ()-> createGameService.createGame(request, "invalid_authtoken"));
    }


}
