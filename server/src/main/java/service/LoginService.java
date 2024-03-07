package service;

import dataAccess.Interface.AuthDAO;
import dataAccess.Interface.UserDAO;
import dataAccess.Memory.AuthDAOMemory;
import dataAccess.Exceptions.BadRequestException;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
import dataAccess.Memory.UserDAOMemory;
import result.LoginResult;

import java.util.UUID;

public class LoginService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public LoginService(UserDAO userDAO, AuthDAO authDAOMemory) {
        this.userDAO = userDAO;
        this.authDAO = authDAOMemory;
    }
    public LoginResult login(String username, String password) throws DataAccessException {
//        if (userDAO.checkUser(username, password)) {
//            String authToken = UUID.randomUUID().toString();
//            authDAO.createAuth(username, authToken);
//            return new LoginResult(username, authToken);
//        }
//        if (!userDAO.checkUser(username, password)) {
//            throw new UnauthorizedException("Unauthorized");
//        }
//        if (userDAO.getUser(username) == null || userDAO.getUser(username).password() == null ||
//                username.isEmpty() || username == null || password.isEmpty() || password == null) {
//            throw new BadRequestException("Bad Request");
//        }
//        else {
//            throw new DataAccessException("Invalid request");
//        }
        if (username == null || password == null || username.isEmpty() || password.isEmpty()){
            throw new BadRequestException("Bad Request");
        }
        if (!userDAO.checkUser(username, password)){
            throw new UnauthorizedException("Unauthorized");
        }
           String authToken = UUID.randomUUID().toString();
           authDAO.createAuth(username, authToken);
           return new LoginResult(username, authToken);
    }
}
//        if (userDAOMemory.checkUser(username, password)) {
//            String authToken = UUID.randomUUID().toString();
//            authDAOMemory.createAuth(username, authToken);
//            return new LoginResult(username, authToken);
//        }
//        if (!userDAOMemory.checkUser(username, password)) {
//            throw new UnauthorizedException("Unauthorized");
//        }
//        if (userDAOMemory.getUser(username) == null || userDAOMemory.getUser(username).password() == null ||
//                username.isEmpty() || username == null || password.isEmpty() || password == null) {
//            throw new BadRequestException("Bad Request");
//        }
//        else {
//            throw new DataAccessException("Invalid request");
//        }
//    }