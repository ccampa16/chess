package websocket;
import webSocketMessages.*;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;

public interface NotificationHandler {
    void notify(Notification notification);
    void error(Error error);
    void loadGame(LoadGame loadGame);
}
