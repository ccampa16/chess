package service;

import dataAccess.*;
import org.eclipse.jetty.server.Authentication;
import result.ClearResult;

public class ClearService {
    private final UserDAOMemory userDaoMemory;
    private final AuthDAOMemory authDAOMemory;
    private final GameDAOMemory gameDAOMemory;

    public ClearService(UserDAOMemory userDaoMemory, AuthDAOMemory authDAOMemory, GameDAOMemory gameDAOMemory) {
        this.userDaoMemory = userDaoMemory;
        this.authDAOMemory = authDAOMemory;
        this.gameDAOMemory = gameDAOMemory;
    }
    public void clear() throws DataAccessException {
        userDaoMemory.clear();
        authDAOMemory.clear();
        gameDAOMemory.clear();
    }
}
