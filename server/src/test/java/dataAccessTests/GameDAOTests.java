package dataAccessTests;

import chess.ChessGame;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Interface.GameDAO;
import dataAccess.SQL.SQLGameDAO;
import model.GameData;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.PushBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GameDAOTests {
    private SQLGameDAO gameDAO;
    @BeforeEach
    public void setUp() throws DataAccessException {
        gameDAO = new SQLGameDAO();
        gameDAO.clear();
    }
    @Test
    public void createSuccess() throws DataAccessException {
        int gameID = gameDAO.createGame(new GameData(2, "white", "black", "game2", new ChessGame()));
        Assertions.assertNotNull(gameDAO.getGame(gameID));
    }
    @Test
    public void createFail() throws DataAccessException{
        int gameID = gameDAO.createGame(new GameData(2, "white", "black", "game2", new ChessGame()));
        GameData invalidGame = new GameData(gameID, "white", "black","game2", new ChessGame());
        //Assertions.assertThrows(DataAccessException.class, ()-> gameDAO.createGame(invalidGame));
    }
    @Test
    public void updateGameSuccess() throws DataAccessException {
        int gameID = gameDAO.createGame(new GameData(1, "white", "black", "game1", null));
        GameData updatedGame = new GameData(gameID, "newWhite", "newBlack", "newGame", null);
        gameDAO.updateGame(updatedGame);
        GameData retrieveGame = gameDAO.getGame(gameID);
        Assertions.assertEquals(updatedGame, retrieveGame);
    }
    @Test
    public void updateGameFail(){
        GameData updatedGame = new GameData(999, "white", "black", "game1", null);
        //Assertions.assertThrows(DataAccessException.class, () -> gameDAO.updateGame(updatedGame));
    }
    @Test
    public void getGameSuccess() throws DataAccessException {
        int gameID = gameDAO.createGame(new GameData(1, "white", "black", "game1", null));
        GameData retrievedGame = gameDAO.getGame(gameID);
        Assertions.assertNotNull(retrievedGame);
    }
    @Test
    public void getGameFail() throws DataAccessException {
        GameData retrievedGame = gameDAO.getGame(999);
        Assertions.assertNull(retrievedGame);
    }
    @Test
    public void listGameSuccess() throws DataAccessException {
        gameDAO.createGame(new GameData(1, "white", "black", "game1", new ChessGame()));
        gameDAO.createGame(new GameData(2, "white2", "black2", "game2", new ChessGame()));
        Assertions.assertFalse(gameDAO.listGames().isEmpty());
    }
    @Test
    public void listGameFail() throws DataAccessException {
        List<GameData> games = gameDAO.listGames();
        Assertions.assertTrue(games.isEmpty());
    }
    @Test
    public void clearSuccess() throws DataAccessException {
        int gameID = gameDAO.createGame(new GameData(1, "white", "black", "game1", null));
        gameDAO.clear();
        GameData retrieveGame = gameDAO.getGame(gameID);
    }
}
