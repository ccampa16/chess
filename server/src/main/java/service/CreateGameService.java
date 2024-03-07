package service;

import chess.ChessGame;
import dataAccess.Interface.AuthDAO;
import dataAccess.Exceptions.BadRequestException;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
import dataAccess.Memory.GameDAOMemory;
import model.GameData;
import dataAccess.Interface.*;
import request.CreateGameRequest;
import result.CreateGameResult;

public class CreateGameService {
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public CreateGameService(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }
    public CreateGameResult createGame(CreateGameRequest req, String authtoken) throws DataAccessException {
        if (!authDAO.checkAuth(authtoken)){
           // return new CreateGameResult(0, "Error: unauthorized");
            throw new UnauthorizedException("unauthorized");

        }
        if(req.getGameName() == null){
            throw new BadRequestException("Bad request");
        }
        int gameID = gameDAO.incrementGameID();
        GameData newGame = new GameData(gameID, null, null, req.getGameName(), new ChessGame());
        gameDAO.createGame(newGame);
        return new CreateGameResult(newGame.gameID());
    }

}
//    private final AuthDAOMemory authDAOMemory;
//    private final GameDAOMemory gameDAOMemory;
//
//    public CreateGameService(AuthDAOMemory authDAOMemory, GameDAOMemory gameDAOMemory) {
//        this.authDAOMemory = authDAOMemory;
//        this.gameDAOMemory = gameDAOMemory;
//    }
//    public CreateGameResult createGame(CreateGameRequest req, String authtoken) throws DataAccessException {
//        if (!authDAOMemory.checkAuth(authtoken)){
//           // return new CreateGameResult(0, "Error: unauthorized");
//            throw new UnauthorizedException("unauthorized");
//
//        }
//        if(req.getGameName() == null){
//            throw new BadRequestException("Bad request");
//        }
//        int gameID = gameDAOMemory.incrementGameID();
//        GameData newGame = new GameData(gameID, null, null, req.getGameName(), new ChessGame());
//        gameDAOMemory.createGame(newGame);
//        return new CreateGameResult(newGame.gameID());
//    }