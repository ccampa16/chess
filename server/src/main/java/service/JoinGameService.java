package service;

import dataAccess.AuthDAOMemory;
import dataAccess.Exceptions.AlreadyTakenException;
import dataAccess.Exceptions.BadRequestException;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedException;
import dataAccess.GameDAOMemory;
import model.GameData;

public class JoinGameService {
    private final GameDAOMemory gameDAOMemory;
    private final AuthDAOMemory authDAOMemory;
    public JoinGameService(GameDAOMemory gameDAOMemory, AuthDAOMemory authDAOMemory){
        this.gameDAOMemory = gameDAOMemory;
        this.authDAOMemory = authDAOMemory;
    }
    public void joinGame(String authToken, String playerColor, int gameID) throws DataAccessException {
        GameData game = gameDAOMemory.getGame(gameID);
        if (authDAOMemory.getAuth(authToken) == null){
            throw new UnauthorizedException("Unauthorized");
        }
        if (gameDAOMemory.getGame(gameID) == null){
            throw new BadRequestException("Bad request");
        }
        if (playerColor != null){
            if (playerColor.equals("WHITE")){
                if (game.whiteUsername() != null) {
                    throw new AlreadyTakenException("Already taken");
                } else {
                    gameDAOMemory.updateGame(new GameData(game.gameID(), authDAOMemory.getAuth(authToken).username(), game.blackUsername(), game.gameName(), game.game()));
                }
                } else if (playerColor.equals("BLACK")){
                if (game.blackUsername() != null){
                    throw new AlreadyTakenException("Already taken");
                } else {
                    gameDAOMemory.updateGame(new GameData(game.gameID(), game.whiteUsername(), authDAOMemory.getAuth(authToken).username(), game.gameName(), game.game()));
                }
            }
        }
    }
}
