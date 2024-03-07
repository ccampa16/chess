package service;

import dataAccess.Interface.AuthDAO;
import dataAccess.Interface.UserDAO;
import dataAccess.Memory.AuthDAOMemory;
import dataAccess.Exceptions.AlreadyTakenException;
import dataAccess.Exceptions.BadRequestException;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Memory.UserDAOMemory;
import model.UserData;
import result.RegisterResult;

import java.util.UUID;

public class RegisterService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public RegisterService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }
    public RegisterResult register(String username, String password, String email) throws DataAccessException{
        if (username == null || password == null || email == null){
            throw new BadRequestException("Bad request");
        }
        UserData newUser = userDAO.getUser(username);
        if (newUser != null){
            throw new AlreadyTakenException("Already taken");
        }
        userDAO.createUser(username, password, email);
        String authToken = generateAuthToken();
        authDAO.createAuth(username, authToken);
        return new RegisterResult(username, authToken);

    }
    private String generateAuthToken(){
        return UUID.randomUUID().toString();
    }
}
