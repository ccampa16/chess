package service;

import dataAccess.AuthDAOMemory;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
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
        if (!authDAOMemory.checkAuth(authtoken)){
            throw new UnauthorizedException("Unauthorized");
        }
        return new ListGamesResult(gameDAOMemory.listGames());
    }
}
