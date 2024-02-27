package handler;

import com.google.gson.Gson;
import dataAccess.Exceptions.BadRequestException;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
import request.CreateGameRequest;
import result.CreateGameResult;
import service.CreateGameService;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class CreateGameHandler {
    private final CreateGameService createGameService;
    public CreateGameHandler(CreateGameService createGameService){
        this.createGameService = createGameService;
    }
    public Object createGame(Request req, Response res){
        try {
            String authtoken = req.headers("authorization");
            CreateGameRequest createGameRequest = new Gson().fromJson(req.body(), CreateGameRequest.class);
            String gameName = createGameRequest.getGameName();
            CreateGameResult createGameResult = createGameService.createGame(createGameRequest, authtoken);
            res.status(200);
            return new Gson().toJson(createGameResult);
        } catch (UnauthorizedException e){
            res.status(401);
            return new Gson().toJson(new ErrorMessage("Error: unauthorized"));
        } catch (BadRequestException e){
            res.status(400);
            return new Gson().toJson(new ErrorMessage("Error: bad request"));
        } catch (DataAccessException e){
            res.status(500);
            return new Gson().toJson(new ErrorMessage("Error: descripton"));
        }
    }
}
