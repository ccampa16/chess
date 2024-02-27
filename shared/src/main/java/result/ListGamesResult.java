package result;

import model.GameData;

import java.util.List;

public class ListGamesResult {
    private List<GameData> games;
    private String message;

    public ListGamesResult(List<GameData> games, String message) {
        this.games = games;
        this.message = message;
    }

    public ListGamesResult(List<GameData> games) {
        this.games = games;
    }

    public List<GameData> getGames() {
        return games;
    }

    public void setGames(List<GameData> games) {
        this.games = games;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
