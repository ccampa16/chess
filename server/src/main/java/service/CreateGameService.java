package service;

import chess.ChessGame;
import dataAccess.AuthDAOMemory;
import dataAccess.Exceptions.BadRequestException;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
import dataAccess.GameDAOMemory;
import model.GameData;
import request.CreateGameRequest;
import result.CreateGameResult;

public class CreateGameService {
    private final AuthDAOMemory authDAOMemory;
    private final GameDAOMemory gameDAOMemory;

    public CreateGameService(AuthDAOMemory authDAOMemory, GameDAOMemory gameDAOMemory) {
        this.authDAOMemory = authDAOMemory;
        this.gameDAOMemory = gameDAOMemory;
    }
    public CreateGameResult createGame(CreateGameRequest req, String authtoken) throws DataAccessException {
        if (!authDAOMemory.checkAuth(authtoken)){
           // return new CreateGameResult(0, "Error: unauthorized");
            throw new UnauthorizedException("unauthorized");

        }
        if(req.getGameName() == null){
            throw new BadRequestException("Bad request");
        }
        int gameID = gameDAOMemory.incrementGameID();
        GameData newGame = new GameData(gameID, null, null, req.getGameName(), new ChessGame());
        gameDAOMemory.createGame(newGame);
        return new CreateGameResult(newGame.gameID());
    }

}
