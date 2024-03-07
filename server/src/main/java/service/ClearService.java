package service;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.Interface.AuthDAO;
import dataAccess.Interface.GameDAO;
import dataAccess.Interface.UserDAO;
import dataAccess.Memory.AuthDAOMemory;
import dataAccess.Memory.GameDAOMemory;
import dataAccess.Memory.UserDAOMemory;

public class ClearService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public ClearService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }
    public void clear() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }
}
//    private final UserDAOMemory userDaoMemory;
//    private final AuthDAOMemory authDAOMemory;
//    private final GameDAOMemory gameDAOMemory;
//
//    public ClearService(UserDAOMemory userDaoMemory, AuthDAOMemory authDAOMemory, GameDAOMemory gameDAOMemory) {
//        this.userDaoMemory = userDaoMemory;
//        this.authDAOMemory = authDAOMemory;
//        this.gameDAOMemory = gameDAOMemory;
//    }
//    public void clear() throws DataAccessException {
//        userDaoMemory.clear();
//        authDAOMemory.clear();
//        gameDAOMemory.clear();
//    }