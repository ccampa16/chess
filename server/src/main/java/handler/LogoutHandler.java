package handler;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import result.LogoutResult;
import service.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler {
    private final LogoutService logoutService;

    public LogoutHandler(LogoutService logoutService) {
        this.logoutService = logoutService;
    }
    public Object logout(Request req, Response res){
        try{
            String authtoken = req.headers("authorization");
            LogoutResult logoutResult = logoutService.logout(authtoken);
            if (logoutResult.getErrorMessage() == null){
                res.status(200);
            } else{
                res.status(401);
            }
            return new Gson().toJson(logoutResult);
        } catch (DataAccessException e){
            res.status(500);
            return new Gson().toJson(new ErrorMessage("Error: description"));
        }
    }
}
