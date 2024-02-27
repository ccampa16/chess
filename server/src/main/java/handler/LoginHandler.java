package handler;

import com.google.gson.Gson;
import dataAccess.Exceptions.BadRequestException;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
import request.LoginRequest;
import result.LoginResult;
import service.LoginService;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class LoginHandler {
    private final LoginService loginService;
    public LoginHandler(LoginService loginService){
        this.loginService = loginService;
    }
    public Object login(Request req, Response res){
        try{
            LoginRequest loginRequest = new Gson().fromJson(req.body(), LoginRequest.class);
            LoginResult loginResult = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return new Gson().toJson(loginResult);
        } catch (UnauthorizedException e){
            res.status(401);
            return new Gson().toJson(new ErrorMessage("Error: unauthorized"));
        } catch (DataAccessException e){
            res.status(500);
            return new Gson().toJson(new ErrorMessage("Error: " + e.getMessage()));
        }

    }
}
