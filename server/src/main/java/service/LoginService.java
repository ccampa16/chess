package service;

import dataAccess.Memory.AuthDAOMemory;
import dataAccess.Exceptions.BadRequestException;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
import dataAccess.Memory.UserDAOMemory;
import result.LoginResult;

import java.util.UUID;

public class LoginService {
    private final UserDAOMemory userDAOMemory;
    private final AuthDAOMemory authDAOMemory;

    public LoginService(UserDAOMemory userDAOMemory, AuthDAOMemory authDAOMemory) {
        this.userDAOMemory = userDAOMemory;
        this.authDAOMemory = authDAOMemory;
    }
    public LoginResult login(String username, String password) throws DataAccessException {
        if (userDAOMemory.checkUser(username, password)) {
            String authToken = UUID.randomUUID().toString();
            authDAOMemory.createAuth(username, authToken);
            return new LoginResult(username, authToken);
        }
        if (!userDAOMemory.checkUser(username, password)) {
            throw new UnauthorizedException("Unauthorized");
        }
        if (userDAOMemory.getUser(username) == null || userDAOMemory.getUser(username).password() == null ||
                username.isEmpty() || username == null || password.isEmpty() || password == null) {
            throw new BadRequestException("Bad Request");
        }
        else {
            throw new DataAccessException("Invalid request");
        }
    }
}
