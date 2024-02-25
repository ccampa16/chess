package service;

import dataAccess.AuthDAOMemory;
import dataAccess.DataAccessException;
import dataAccess.GameDAOMemory;
import result.CreateGameResult;
import spark.Request;

import java.util.UUID;

public class CreateGameService {
    private final AuthDAOMemory authDAOMemory;
    private final GameDAOMemory gameDAOMemory;

    public CreateGameService(AuthDAOMemory authDAOMemory, GameDAOMemory gameDAOMemory) {
        this.authDAOMemory = authDAOMemory;
        this.gameDAOMemory = gameDAOMemory;
    }
    //are we supposed to generate the gameID?/
    public CreateGameResult createGame(Request req, String authtoken) throws DataAccessException {
        if (!authDAOMemory.checkAuth(authtoken)){
            return new CreateGameResult(0, "Error: unauthorized");

        }
        int gameID = generateGameID();
        //add logic for creating a new game

        return new CreateGameResult(gameID, null);
    }
    private int generateGameID(){
        return UUID.randomUUID().hashCode();
    }
}
