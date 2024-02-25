package result;

import model.GameData;
import request.ListGamesRequest;

import java.util.List;

public class ListGamesResult {
    private List<GameData> games;
    private String errorMessage;

    public ListGamesResult(List<GameData> games, String errorMessage) {
        this.games = games;
        this.errorMessage = errorMessage;
    }

    public List<GameData> getGames() {
        return games;
    }

    public void setGames(List<GameData> games) {
        this.games = games;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
