package dataAccess;

import model.GameData;


import java.util.HashSet;

public class GameDAOMemory implements GameDAO{
    private HashSet<GameData> db;
    GameDAOMemory(){
        db = new HashSet<>();
    }

    @Override
    public void createGame(String username, String password){
//        GameData newGame = new GameData(username, password);
//        db.add(newGame);
    }

    @Override
    public GameData getGames(String username) {
        for (GameData game : db){
            if (game.getBlackUsername().equals(username) || game.getWhiteUsername().equals(username)){
                return game;
            }
        }
        return null;
    }
    @Override
    public void updateGame(String gameID, String clientColor, String username) {
        for(GameData game : db){
            String gameID2 = String.valueOf(game.getGameID());
            if (gameID2 == gameID){
                //game.
            }
        }
    }
    @Override
    public void clear(){
        db.clear();
    }
    //
}
