package service;

import dataAccess.AuthDAOMemory;
import dataAccess.DataAccessException;
import dataAccess.GameDAOMemory;
import model.GameData;
import result.ListGamesResult;

import java.util.List;

public class ListGamesService {
    private final AuthDAOMemory authDAOMemory;
    private final GameDAOMemory gameDAOMemory;

    public ListGamesService(AuthDAOMemory authDAOMemory, GameDAOMemory gameDAOMemory) {
        this.authDAOMemory = authDAOMemory;
        this.gameDAOMemory = gameDAOMemory;
    }
    public ListGamesResult listGames(String authtoken) throws DataAccessException {
        if (authDAOMemory.getAuth(authtoken) == null){
            return new ListGamesResult(null, "Error: unauthorized");
        }
        if(!authDAOMemory.checkAuth(authtoken)){
            return new ListGamesResult(null, "Error: unauthorized");
        }
        List<GameData> games = gameDAOMemory.listGames();
        return new ListGamesResult(games, null);
    }
}
