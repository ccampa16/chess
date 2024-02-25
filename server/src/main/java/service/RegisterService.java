package service;

import dataAccess.AuthDAOMemory;
import dataAccess.DataAccessException;
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

    public RegisterResult register(String username, String password, String email) throws DataAccessException {
        if(userDAOMemory.getUser(username) != null){
            return new RegisterResult(null, "Error: already taken");
        }
        UserData newUser = userDAOMemory.createUser(username, password, email);
        String authToken = generateAuthToken();
        authDAOMemory.createAuth(username, authToken);

        return new RegisterResult(newUser.username(), UUID.randomUUID().toString());
    }
    private String generateAuthToken(){
        return UUID.randomUUID().toString();
    }
}
