package dataAccess;
import chess.ChessGame;
import model.GameData;
import model.UserData;

import java.util.List;

public interface GameDAO {
    void createGame(GameData newGame) throws DataAccessException;
    List<GameData> listGames() throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    void updateGame(GameData updatedGame) throws DataAccessException;
    void clear() throws DataAccessException;
}
