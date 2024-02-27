package service;

import dataAccess.AuthDAOMemory;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;

public class LogoutService {
    private final AuthDAOMemory authDAOMemory;
    public LogoutService(AuthDAOMemory authDAOMemory){
        this.authDAOMemory = authDAOMemory;
    }
    public void logout(String authtoken) throws DataAccessException{
        if (!authDAOMemory.checkAuth(authtoken)){
            throw new UnauthorizedException("unauthorized");
        }
        authDAOMemory.deleteAuth(authtoken);
    }
}
