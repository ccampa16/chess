package handler;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import result.ListGamesResult;
import service.ListGamesService;
import spark.Request;
import spark.Response;

public class ListGamesHandler {
    private final ListGamesService listGamesService;

    public ListGamesHandler(ListGamesService listGamesService) {
        this.listGamesService = listGamesService;
    }
    public Object listGames(Request req, Response res){
        try{
            String authToken = req.headers("authorization");
            ListGamesResult listGamesResult = listGamesService.listGames(authToken);
            if (listGamesResult.getErrorMessage() == null){
                res.status(200);
                return new Gson().toJson(listGamesResult);
            } else {
                res.status(401);
                return new Gson().toJson(new ErrorMessage(listGamesResult.getErrorMessage()));
            }
            //return new Gson().toJson(listGamesResult);
        } catch (DataAccessException e){
            res.status(500);
            return new Gson().toJson(new ErrorMessage("Error: description"));
        }
    }
}

