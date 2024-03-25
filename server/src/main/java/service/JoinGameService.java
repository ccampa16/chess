package service;

import dataAccess.Interface.AuthDAO;
import dataAccess.Interface.GameDAO;
import dataAccess.Memory.AuthDAOMemory;
import dataAccess.Exceptions.AlreadyTakenException;
import dataAccess.Exceptions.BadRequestException;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
import dataAccess.Memory.GameDAOMemory;
import model.GameData;

public class JoinGameService {
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;
    public JoinGameService(GameDAO gameDAO, AuthDAO authDAO){
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }
    public void joinGame(String authToken, String playerColor, int gameID) throws DataAccessException {
        GameData game = gameDAO.getGame(gameID);
        if (authDAO.getAuth(authToken) == null){
            throw new UnauthorizedException("Unauthorized");
        }
        if (gameDAO.getGame(gameID) == null){
            throw new BadRequestException("Bad request");
        }
        String username = authDAO.getAuth(authToken).username();
        if (playerColor != null) {
            if (playerColor.equals("WHITE") && game.whiteUsername() != null) {
                //if (game.whiteUsername() != null) {
                if (!username.equals(game.whiteUsername())) {
                    throw new AlreadyTakenException("Already taken");
                } else {
                    gameDAO.updateGame(new GameData(game.gameID(), authDAO.getAuth(authToken).username(), game.blackUsername(), game.gameName(), game.game()));
                }
            } else if (playerColor.equals("BLACK") && game.blackUsername() != null) {
                //if (game.blackUsername() != null){
                if (!username.equals(game.blackUsername())) {
                    throw new AlreadyTakenException("Already taken");
                } else {
                    gameDAO.updateGame(new GameData(game.gameID(), game.whiteUsername(), authDAO.getAuth(authToken).username(), game.gameName(), game.game()));
                }
            } else {
                gameDAO.updateGame(new GameData(game.gameID(), authDAO.getAuth(authToken).username(), game.blackUsername(), game.gameName(), game.game()));
            }
        }
    }
}
//private final GameDAOMemory gameDAOMemory;
//    private final AuthDAOMemory authDAOMemory;
//    public JoinGameService(GameDAOMemory gameDAOMemory, AuthDAOMemory authDAOMemory){
//        this.gameDAOMemory = gameDAOMemory;
//        this.authDAOMemory = authDAOMemory;
//    }
//    public void joinGame(String authToken, String playerColor, int gameID) throws DataAccessException {
//        GameData game = gameDAOMemory.getGame(gameID);
//        if (authDAOMemory.getAuth(authToken) == null){
//            throw new UnauthorizedException("Unauthorized");
//        }
//        if (gameDAOMemory.getGame(gameID) == null){
//            throw new BadRequestException("Bad request");
//        }
//        if (playerColor != null){
//            if (playerColor.equals("WHITE")){
//                if (game.whiteUsername() != null) {
//                    throw new AlreadyTakenException("Already taken");
//                } else {
//                    gameDAOMemory.updateGame(new GameData(game.gameID(), authDAOMemory.getAuth(authToken).username(), game.blackUsername(), game.gameName(), game.game()));
//                }
//                } else if (playerColor.equals("BLACK")){
//                if (game.blackUsername() != null){
//                    throw new AlreadyTakenException("Already taken");
//                } else {
//                    gameDAOMemory.updateGame(new GameData(game.gameID(), game.whiteUsername(), authDAOMemory.getAuth(authToken).username(), game.gameName(), game.game()));
//                }
//            }
//        }
//    }