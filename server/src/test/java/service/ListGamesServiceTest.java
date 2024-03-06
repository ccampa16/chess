package service;

import chess.ChessGame;
import dataAccess.Memory.AuthDAOMemory;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Memory.GameDAOMemory;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.ListGamesResult;

public class ListGamesServiceTest {
    private AuthDAOMemory authDAOMemory;
    private GameDAOMemory gameDAOMemory;
    private ListGamesService listGamesService;
    @BeforeEach
    public void setUp(){
        authDAOMemory = new AuthDAOMemory();
        gameDAOMemory = new GameDAOMemory();
        listGamesService = new ListGamesService(authDAOMemory, gameDAOMemory);
    }
    @Test
    public void listGamesSuccess() throws DataAccessException{
        String authToken = "validToken";
        authDAOMemory.createAuth("username", authToken);

        GameData game1 = new GameData(1, "white", "black",
                "Game1", new ChessGame());
        GameData game2 = new GameData(2, "white", "black",
                "Game2", new ChessGame());

        gameDAOMemory.createGame(game1);
        gameDAOMemory.createGame(game2);

        ListGamesResult result = listGamesService.listGames(authToken);
        Assertions.assertEquals(2, result.getGames().size());
    }
    @Test
    public void listGamesFail() throws DataAccessException {
        String authtoken = "invalidToken";
        authDAOMemory.createAuth("username", authtoken);
        ListGamesResult result = listGamesService.listGames(authtoken);
        Assertions.assertThrows(DataAccessException.class, () -> listGamesService.listGames("invalidAuth"));

    }
}
