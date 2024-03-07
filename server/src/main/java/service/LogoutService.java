package service;

import dataAccess.Interface.AuthDAO;
import dataAccess.Memory.AuthDAOMemory;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;

public class LogoutService {
    private final AuthDAO authDAO;
    public LogoutService(AuthDAO authDAO){
        this.authDAO = authDAO;
    }
    public void logout(String authtoken) throws DataAccessException{
        if (!authDAO.checkAuth(authtoken)){
            throw new UnauthorizedException("unauthorized");
        }
        authDAO.deleteAuth(authtoken);
    }
}
