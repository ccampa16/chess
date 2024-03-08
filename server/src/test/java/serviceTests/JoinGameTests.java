package serviceTests;

import dataAccess.Memory.AuthDAOMemory;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Memory.GameDAOMemory;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.JoinGameService;

public class JoinGameTests {
    private AuthDAOMemory authDAOMemory;
    private GameDAOMemory gameDAOMemory;
    private JoinGameService joinGameService;
    @BeforeEach
    public void setUp(){
        authDAOMemory = new AuthDAOMemory();
        gameDAOMemory = new GameDAOMemory();
        joinGameService = new JoinGameService(gameDAOMemory, authDAOMemory);
    }
    @Test
    public void joinGameSuccess() throws DataAccessException {
        String authtoken = "validAuth";
        authDAOMemory.createAuth("user", authtoken);
        int gameID = gameDAOMemory.incrementGameID();
        gameDAOMemory.createGame(new GameData(gameID, "WHITE", null,
                "TestGame", null));

        joinGameService.joinGame(authtoken, "BLACK", gameID);
        GameData updateGame = gameDAOMemory.getGame(gameID);
        if ("BLACK".equals("WHITE")){
            Assertions.assertEquals("user", updateGame.whiteUsername());
        } else {
            Assertions.assertEquals("user", updateGame.blackUsername());
        }
    }
    @Test
    public void joinGameFail(){
        String authtoken = "validAuth";
        authDAOMemory.createAuth("user", authtoken);
        int gameID = gameDAOMemory.incrementGameID();
        Assertions.assertThrows(DataAccessException.class, ()-> joinGameService.joinGame("validAuth", "WHITE", gameID));
        Assertions.assertThrows(DataAccessException.class, ()-> joinGameService.joinGame("validAuth", "WHITE", 9999));

    }
}
