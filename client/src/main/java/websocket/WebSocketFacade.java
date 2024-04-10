package websocket;

import javax.websocket.Endpoint;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import ui.ResponseException;
import webSocketMessages.*;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;


import javax.websocket.*;
import java.io.IOException;
import java.net.NetPermission;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {
    Session session;
    NotificationHandler notificationHandler;


    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch (serverMessage.getServerMessageType()){
                        case ERROR: {
                            Error errorMessage = new Gson().fromJson(message, Error.class);
                            notificationHandler.error(errorMessage);
                        }
                        case LOAD_GAME:{
                            LoadGame loadGameMessage = new Gson().fromJson(message, LoadGame.class);
                            notificationHandler.loadGame(loadGameMessage);
                        }
                        case NOTIFICATION: {
                            Notification notificationMessage = new Gson().fromJson(message, Notification.class);
                            notificationHandler.notify(notificationMessage);
                        }
                    }
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(ex.getMessage());
        }
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
    private void sendMessage(UserGameCommand command) throws IOException{
        session.getBasicRemote().sendText(new Gson().toJson(command));
    }
    public void joinPlayer(String authToken, int gameID, ChessGame.TeamColor team) throws IOException {
        this.sendMessage(new JoinPlayer(authToken, gameID, team));
    }

    public void joinObserver(String authToken, int gameID) throws IOException {
        this.sendMessage(new JoinObserver(authToken, gameID));
    }

    public void makeMove(String authToken, int gameID, ChessMove move) throws IOException {
        this.sendMessage(new MakeMove(authToken, gameID, move));
    }

    public void leaveGame(String authToken, int gameID) throws IOException {
        this.sendMessage(new Leave(authToken, gameID));
    }

    public void resignGame(String authToken, int gameID) throws IOException {
        this.sendMessage(new Resign(authToken, gameID));
    }



}
