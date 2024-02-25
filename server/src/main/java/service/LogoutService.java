package service;

import dataAccess.AuthDAOMemory;
import dataAccess.DataAccessException;
import result.LogoutResult;

public class LogoutService {
    private final AuthDAOMemory authDAOMemory;
    public LogoutService(AuthDAOMemory authDAOMemory){
        this.authDAOMemory = authDAOMemory;
    }
    public LogoutResult logout(String authtoken) throws DataAccessException{
        if (authDAOMemory.getAuth(authtoken) == null){
            return new LogoutResult("Error: unauthorized");
        }
        authDAOMemory.deleteAuth(authtoken);
        return new LogoutResult(null);
    }
}
