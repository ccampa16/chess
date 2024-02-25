package service;

import dataAccess.AuthDAOMemory;
import dataAccess.DataAccessException;
import dataAccess.UserDAOMemory;
import result.LoginResult;

import java.util.UUID;

public class LoginService {
    private final UserDAOMemory userDAOMemory;
    private final AuthDAOMemory authDAOMemory;

    public LoginService(UserDAOMemory userDAOMemory, AuthDAOMemory authDAOMemory) {
        this.userDAOMemory = userDAOMemory;
        this.authDAOMemory = authDAOMemory;
    }
    public LoginResult login(String username, String password) throws DataAccessException{
        if (!userDAOMemory.checkUser(username, password)){
            return new LoginResult("Error: unauthorized");
        }
        String authToken = UUID.randomUUID().toString();
        authDAOMemory.createAuth(username, authToken);
        return new LoginResult(username, authToken);
    }
}
