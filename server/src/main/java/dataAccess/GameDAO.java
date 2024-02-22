package dataAccess;
import model.GameData;
import model.UserData;

public interface GameDAO {
    void createGame(String authToken, String gameName) throws DataAccessException;
    GameData getGames(String username) throws DataAccessException;
    void updateGame(String gameID, String clientColor, String username) throws DataAccessException;
    void clear() throws DataAccessException;
}
