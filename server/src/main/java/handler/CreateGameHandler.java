package handler;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import request.CreateGameRequest;
import result.CreateGameResult;
import service.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    private final CreateGameService createGameService;
    public CreateGameHandler(CreateGameService createGameService){
        this.createGameService = createGameService;
    }
    public Object createGame(Request req, Response res){
        try{
            String authtoken = req.headers("authorization");
            CreateGameRequest createGameRequest = new Gson().fromJson(req.body(), CreateGameRequest.class);
            String gameName = createGameRequest.getGameName();
            CreateGameResult createGameResult = createGameService.createGame(req, authtoken);

            if (createGameResult.getErrorMessage() == null){
                res.status(200);
                return new Gson().toJson(createGameRequest);
            } else{
                res.status(400);
                return new Gson().toJson(new ErrorMessage(createGameResult.getErrorMessage()));
            }
        } catch (DataAccessException e){
            res.status(500);
            return new Gson().toJson(new ErrorMessage("Error: descripton"));
        }
    }
}
