package result;

public class CreateGameResult {
    private int gameID;
    private String errorMessage;

    public CreateGameResult(int gameID, String errorMessage) {
        this.gameID = gameID;
        this.errorMessage = errorMessage;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
