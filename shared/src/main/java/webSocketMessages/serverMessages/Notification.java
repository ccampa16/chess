package webSocketMessages.serverMessages;

import com.google.gson.Gson;

public class Notification extends ServerMessage{
    private final String message;

    public Notification(ServerMessageType type, String message) {
        super(type);
        this.message = message;
    }
    public String getMessage(){
        return message;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public static Notification fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Notification.class);
    }

}
