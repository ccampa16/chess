package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import org.eclipse.jetty.server.Authentication;
import result.ClearResult;

public class ClearService {
    private final UserDAO userDao;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public ClearService(UserDAO userDao, AuthDAO authDAO, GameDAO gameDAO) {
        this.userDao = userDao;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }
    public void clear() throws DataAccessException {
        userDao.clear();
        authDAO.clear();
        gameDAO.clear();
    }
}
