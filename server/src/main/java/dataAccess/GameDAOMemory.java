package dataAccess;

import chess.ChessGame;
import model.GameData;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GameDAOMemory implements GameDAO{
    private static HashSet<GameData> db;
    private int currentGameID;

    public GameDAOMemory(){
        db = new HashSet<>();
        currentGameID = 1;
    }
    @Override
    public List<GameData> listGames() {
        return new ArrayList<>(db);
    }
    @Override
    public GameData getGame(int gameId){
        for (GameData game : db){
            if (game.gameID() == gameId){
                return game;
            }
        }
        return null;
    }

    @Override
    public void createGame(GameData newGame){
        GameData addedGame;
        addedGame = new GameData(currentGameID, newGame.whiteUsername(), newGame.blackUsername(), newGame.gameName(), newGame.game());
        db.add(addedGame);
    }

    @Override
    public void updateGame(GameData updatedGame) {
        db.removeIf(game -> game.gameID() == updatedGame.gameID()); //delete
        db.add(updatedGame);
    }
    @Override
    public void clear(){
        db.clear();
        currentGameID = 1;
    }
    public int incrementGameID(){
        currentGameID++;
        return currentGameID;
    }

}
