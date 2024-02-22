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
    }

    @Override
    public GameData getGames(String username) {
        for (GameData game : db){
            if (game.blackUsername().equals(username) || game.whiteUsername().equals(username)){
                return game;
            }
        }
        return null;
    }



    @Override
    public void updateGame(String gameID, String clientColor, String username) {
    }
    @Override
    public void clear(){
        db.clear();
    }

}
