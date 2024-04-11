package websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.Exceptions.BadRequestException;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Interface.AuthDAO;
import dataAccess.Interface.GameDAO;
import dataAccess.Interface.UserDAO;
import handler.ErrorMessage;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.websocket.api.annotations.*;
import service.JoinGameService;
import service.LoginService;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;
import org.eclipse.jetty.websocket.api.*;
import dataAccess.DatabaseManager.*;
import webSocketMessages.userCommands.*;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    private GameDAO gameDAO;
    private AuthDAO authDAO;
    private UserDAO userDAO;
    private final JoinGameService joinGameService;
    private final LoginService loginService;
    private final Gson gson;
    public WebSocketHandler(Gson gson, JoinGameService joinGameService, LoginService loginService){
        this.gson = gson;
        this.joinGameService = joinGameService;
        this.loginService = loginService;
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

    private void join(Session session, String msg) throws DataAccessException, IOException {
        //if (msg.length() > 1) {
            var joinPlayer = gson.fromJson(msg, JoinPlayer.class);
            int gameID = joinPlayer.getGameID();
            String auth = joinPlayer.getAuthString();
            ChessGame.TeamColor playerColor = joinPlayer.getPlayerColor();

            GameData gameData = joinGameService.getGame(gameID);
            if (gameData == null){
                sendErrorMessage(session, "Error: Bad game ID");
                return;
            }

            String username = loginService.getUser(auth);

            ServerMessage loadGameMessage = new LoadGame(gameData);

            connections.add(username, session);
            session.getRemote().sendString(gson.toJson(loadGameMessage));

            String notificationMessage = String.format("%s joined the game as %s", username, playerColor);
            Notification notification = new Notification(notificationMessage);

            connections.broadcast(username, notification);

        //}

    }

    private void sendErrorMessage(Session session, String errorMsg) throws IOException {
        ErrorMessage errorMessage = new ErrorMessage(errorMsg);
        session.getRemote().sendString(gson.toJson(errorMessage));
    }



}

