package dataAccess.Interface;
import dataAccess.Exceptions.DataAccessException;
import model.GameData;

import java.util.List;

public interface GameDAO {
    void createGame(GameData newGame) throws DataAccessException;
    List<GameData> listGames() throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    void updateGame(GameData updatedGame) throws DataAccessException;
    void clear() throws DataAccessException;
//    int incrementGameID() throws DataAccessException;
}
