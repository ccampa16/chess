package dataAccess;

import chess.ChessGame;
import model.GameData;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GameDAOMemory implements GameDAO{
    private HashSet<GameData> db;
    public GameDAOMemory(){
        db = new HashSet<>();
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
        //GameData newGame = new GameData(newGame, whiteUsername, blackUsername, gameName, game);
        db.add(newGame);
    }

    @Override
    public void updateGame(GameData updatedGame) {
        db.removeIf(game -> game.gameID() == updatedGame.gameID()); //delete
        db.add(updatedGame);
    }
    @Override
    public void clear(){
        db.clear();
    }

}
