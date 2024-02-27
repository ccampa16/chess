package handler;

import com.google.gson.Gson;
import dataAccess.Exceptions.BadRequestException;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
import request.JoinGameRequest;
import service.JoinGameService;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class JoinGameHandler {
    private final JoinGameService joinGameService;

    public JoinGameHandler(JoinGameService joinGameService) {
        this.joinGameService = joinGameService;
    }
    public Object joinGame(Request req, Response res){
        String authToken = req.headers("authorization");
        JoinGameRequest joinGameRequest = new Gson().fromJson(req.body(), JoinGameRequest.class);
        try {
            joinGameService.joinGame(authToken, joinGameRequest.getPlayerColor(), joinGameRequest.getGameID());
            res.status(200);
            return "{}";
        } catch (UnauthorizedException e) {
            res.status(401);
            return new Gson().toJson(new ErrorMessage("Error: unauthorized"));
        } catch (BadRequestException e){
            res.status(400);
            return new Gson().toJson(new ErrorMessage("Error: bad request"));
        } catch (DataAccessException e){
            res.status(403);
            return new Gson().toJson(new ErrorMessage("Error: already taken"));
        }
    }
}



//        } catch (DataAccessException e){
//            if (Objects.equals("Unauthorized", e.getMessage())){
//                res.status(401);
//                return new Gson().toJson(new ErrorMessage("Error: unauthorized"));
//            }
//            if (Objects.equals("Bad request", e.getMessage())){
//                res.status(400);
//                return new Gson().toJson(new ErrorMessage("Error: bad request"));
//            }
//            if (Objects.equals("Already taken", e.getMessage())){
//                res.status(403);
//                return new Gson().toJson(new ErrorMessage("Error: already taken"));
//            }
//            res.status(500);
//            return new Gson().toJson(new ErrorMessage("Error: " + e.getMessage()));
//        } catch (Exception e){
//            res.status(500);
//            return new Gson().toJson(new ErrorMessage("Error: " + e.getMessage()));
//        }
