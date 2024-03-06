package service;

import dataAccess.Memory.AuthDAOMemory;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
import dataAccess.Memory.GameDAOMemory;
import result.ListGamesResult;

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
