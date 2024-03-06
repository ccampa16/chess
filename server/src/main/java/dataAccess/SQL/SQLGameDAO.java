package dataAccess.SQL;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.GameDAO;
import model.GameData;

import java.util.List;

public class SQLGameDAO extends ParentSQL implements GameDAO {
    public SQLGameDAO() throws DataAccessException {
        String[] statements = {
            """
            CREATE TABLE IF NOT EXISTS game (
            'gameID' int NOT NULL,
            'whiteUsername' varchar(200),
            'blackUsername' varchar(200),
            'gameName' varchar(200),
            'game' varchar(200),
            PRIMARY KEY ('gameID')
            )
           """
        };
        createDatabase(statements);

    }

    @Override
    public void createGame(GameData newGame) throws DataAccessException {

    }

    @Override
    public List<GameData> listGames() throws DataAccessException {
        return null;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public void updateGame(GameData updatedGame) throws DataAccessException {

    }

    @Override
    public void clear() throws DataAccessException {

    }
}
