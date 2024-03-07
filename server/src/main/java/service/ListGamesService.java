package service;

import dataAccess.Interface.AuthDAO;
import dataAccess.Interface.GameDAO;
import dataAccess.Memory.AuthDAOMemory;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
import dataAccess.Memory.GameDAOMemory;
import result.ListGamesResult;

public class ListGamesService {
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public ListGamesService(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }
    public ListGamesResult listGames(String authtoken) throws DataAccessException {
        if (!authDAO.checkAuth(authtoken)){
            throw new UnauthorizedException("Unauthorized");
        }
        return new ListGamesResult(gameDAO.listGames());
    }
}
