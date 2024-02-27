package handler;

import com.google.gson.Gson;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
import request.ListGamesRequest;
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
            res.status(200);
            return new Gson().toJson(listGamesResult);
        } catch (UnauthorizedException e){
            res.status(401);
            return new Gson().toJson(new ErrorMessage("Error: unauthorized"));
        } catch (Exception e){
            res.status(500);
            return new Gson().toJson(new ErrorMessage("Error: description"));
        }
    }
}

