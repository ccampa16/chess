package websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.Exceptions.BadRequestException;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.websocket.api.annotations.*;
import service.JoinGameService;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;
import org.eclipse.jetty.websocket.api.*;
import dataAccess.DatabaseManager.*;
import webSocketMessages.userCommands.*;

@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    private final JoinGameService joinGameService;
    private final Gson gson;
    public WebSocketHandler(Gson gson, JoinGameService joinGameService){
        this.gson = gson;
        this.joinGameService = joinGameService;
    }
    @OnWebSocketMessage
    public void onMessage(Session session, String msg) throws Exception {
        UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);


        try{
            switch (command.getCommandType()) {
                case UserGameCommand.CommandType.JOIN_PLAYER -> this.join(session, msg);
//                case UserGameCommand.CommandType.JOIN_OBSERVER -> observe(session, msg);
//                case UserGameCommand.CommandType.MAKE_MOVE -> move(session, msg);
//                case UserGameCommand.CommandType.LEAVE -> leave(session, msg);
//                case UserGameCommand.CommandType.RESIGN -> resign(session, msg);

            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
    }

    private void join(Session session, String msg){
        if (msg.length() > 1) {
            var joinPlayer = gson.fromJson(msg, JoinPlayer.class);
        }

    }

    private void sendErrorMessage(Session session, String errorMsg){

    }



}
