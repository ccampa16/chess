package webSocketMessages.serverMessages;

import model.GameData;

public class LoadGame extends ServerMessage{
    private GameData game;
    public LoadGame(ServerMessageType type, String gameData) {
        super(type);
        this.game = game;
    }
    public GameData getGameData(){
        return game;
    }
}
