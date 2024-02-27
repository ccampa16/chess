package service;

import dataAccess.AuthDAOMemory;
import dataAccess.Exceptions.AlreadyTakenException;
import dataAccess.Exceptions.BadRequestException;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.UserDAOMemory;
import model.UserData;
import result.RegisterResult;

import java.util.UUID;

public class RegisterService {
    private final UserDAOMemory userDAOMemory;
    private final AuthDAOMemory authDAOMemory;

    public RegisterService(UserDAOMemory userDAOMemory, AuthDAOMemory authDAOMemory) {
        this.userDAOMemory = userDAOMemory;
        this.authDAOMemory = authDAOMemory;
    }
    public RegisterResult register(String username, String password, String email) throws DataAccessException{
        if (username == null || password == null || email == null){
            throw new BadRequestException("Bad request");
        }
        UserData newUser = userDAOMemory.getUser(username);
        if (newUser != null){
            throw new AlreadyTakenException("Already taken");
        }
        userDAOMemory.createUser(username, password, email);
        String authToken = generateAuthToken();
        authDAOMemory.createAuth(username, authToken);
        return new RegisterResult(username, authToken);

    }
    private String generateAuthToken(){
        return UUID.randomUUID().toString();
    }
}
