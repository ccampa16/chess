package handler;

import com.google.gson.Gson;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
import service.LogoutService;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class LogoutHandler {
    private final LogoutService logoutService;

    public LogoutHandler(LogoutService logoutService) {
        this.logoutService = logoutService;
    }
    public Object logout(Request req, Response res){
        try {
            String authtoken = req.headers("Authorization");
            logoutService.logout(authtoken);
            res.status(200);
            return "{}";
        } catch (UnauthorizedException e){
            res.status(401);
            return new Gson().toJson(new ErrorMessage("Error: unauthorized"));
        } catch (DataAccessException e) {
            return new Gson().toJson(new ErrorMessage("Error: description"));
        }
    }
}
