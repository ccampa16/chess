package handler;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import request.LoginRequest;
import result.LoginResult;
import service.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler {
    private final LoginService loginService;
    public LoginHandler(LoginService loginService){
        this.loginService = loginService;
    }
    public Object login(Request req, Response res){
        try{
            LoginRequest loginRequest = new Gson().fromJson(req.body(), LoginRequest.class);
            LoginResult loginResult = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
            if (loginResult.getErrorMessage() == null){
                res.status(200);
                return new Gson().toJson(loginResult);
            } else {
                res.status(401);
                return new Gson().toJson(new ErrorMessage(loginResult.getErrorMessage()));
            }
        } catch (DataAccessException e){
            res.status(500);
            return new Gson().toJson(new ErrorMessage("Error: description"));

        }
    }
}
