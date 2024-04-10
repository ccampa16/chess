package server;

import com.google.gson.Gson;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.Interface.AuthDAO;
import dataAccess.Memory.AuthDAOMemory;
import dataAccess.Memory.GameDAOMemory;
import dataAccess.Memory.UserDAOMemory;
import dataAccess.SQL.SQLAuthDAO;
import dataAccess.SQL.SQLGameDAO;
import dataAccess.SQL.SQLUserDAO;
import handler.*;
import service.*;
import spark.*;
import websocket.WebSocketHandler;


public class Server {

    private ClearService clearService;
    private RegisterService registerService;
    private LoginService loginService;
    private LogoutService logoutService;
    private ListGamesService listGamesService;
    private CreateGameService createGameService;
    private JoinGameService joinGameService;
    private WebSocketHandler websocketHandler;
    private ClearHandler clearHandler;
    private RegisterHandler registerHandler;
    private LoginHandler loginHandler;
    private LogoutHandler logoutHandler;
    private ListGamesHandler listGamesHandler;
    private CreateGameHandler createGameHandler;
    private JoinGameHandler joinGameHandler;
    private Gson gson;
    public Server(){
        try {
            SQLUserDAO sqlUserDAO = new SQLUserDAO();
            SQLGameDAO sqlGameDAO = new SQLGameDAO();
            SQLAuthDAO sqlAuthDAO = new SQLAuthDAO();

            clearService = new ClearService(sqlUserDAO, sqlAuthDAO, sqlGameDAO);
            clearHandler = new ClearHandler(clearService);

            registerService = new RegisterService(sqlUserDAO, sqlAuthDAO);
            registerHandler = new RegisterHandler(registerService);

            loginService = new LoginService(sqlUserDAO, sqlAuthDAO);
            loginHandler = new LoginHandler(loginService);

            logoutService = new LogoutService(sqlAuthDAO);
            logoutHandler = new LogoutHandler(logoutService);

            listGamesService = new ListGamesService(sqlAuthDAO, sqlGameDAO);
            listGamesHandler = new ListGamesHandler(listGamesService);


            createGameService = new CreateGameService(sqlAuthDAO, sqlGameDAO);
            createGameHandler = new CreateGameHandler(createGameService);

            joinGameService = new JoinGameService(sqlGameDAO, sqlAuthDAO);
            joinGameHandler = new JoinGameHandler(joinGameService);


            websocketHandler = new WebSocketHandler(gson, joinGameService);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        Spark.webSocket("/connect", websocketHandler);

        createRoutes();


        Spark.awaitInitialization();
        return Spark.port();
    }
    private  void createRoutes () {
        Spark.before((req, res) -> System.out.println("Executing route: " + req.pathInfo()));


//        ClearService clearService = null;
//        try {
//            clearService = new ClearService(new SQLUserDAO(), new SQLAuthDAO(), new SQLGameDAO());
//        } catch (DataAccessException e) {
//            throw new RuntimeException(e);
//        }
//        ClearHandler clearHandler = new ClearHandler(clearService);
        Spark.delete("/db", (request, response) -> clearHandler.clear(request, response));

//        RegisterService registerService = null;
//        try {
//            registerService = new RegisterService(new SQLUserDAO(), new SQLAuthDAO());
//        } catch (DataAccessException e) {
//            throw new RuntimeException(e);
//        }
//        RegisterHandler registerHandler = new RegisterHandler(registerService);
        Spark.post("/user", (request, response) -> registerHandler.register(request, response));

//        LoginService loginService = null;
//        try {
//            loginService = new LoginService(new SQLUserDAO(), new SQLAuthDAO());
//        } catch (DataAccessException e) {
//            throw new RuntimeException(e);
//        }
//        LoginHandler loginHandler = new LoginHandler(loginService);
        Spark.post("/session", (request, response) -> loginHandler.login(request, response));

//        LogoutService logoutService = null;
//        try {
//            logoutService = new LogoutService(new SQLAuthDAO());
//        } catch (DataAccessException e) {
//            throw new RuntimeException(e);
//        }
//        LogoutHandler logoutHandler = new LogoutHandler(logoutService);
        Spark.delete("/session", (request, response) -> logoutHandler.logout(request, response));

//        ListGamesService listGamesService = null;
//        try {
//            listGamesService = new ListGamesService(new SQLAuthDAO(), new SQLGameDAO());
//        } catch (DataAccessException e) {
//            throw new RuntimeException(e);
//        }
//        ListGamesHandler listGamesHandler = new ListGamesHandler(listGamesService);
        Spark.get("/game", (request, response) -> listGamesHandler.listGames(request, response));

//        CreateGameService createGameService = null;
//        try {
//            createGameService = new CreateGameService(new SQLAuthDAO(), new SQLGameDAO());
//        } catch (DataAccessException e) {
//            throw new RuntimeException(e);
//        }
//        CreateGameHandler createGameHandler = new CreateGameHandler(createGameService);
        Spark.post("/game", (request, response) -> createGameHandler.createGame(request, response));

//        JoinGameService joinGameService = null;
//        try {
//            joinGameService = new JoinGameService(new SQLGameDAO(), new SQLAuthDAO());
//        } catch (DataAccessException e) {
//            throw new RuntimeException(e);
//        }
//        JoinGameHandler joinGameHandler = new JoinGameHandler(joinGameService);
        Spark.put("/game", (request, response) -> joinGameHandler.joinGame(request, response));


        //WEBSOCKET

    }


    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
    public static void main(String[] args) {
        new Server().run(8080);
    }
}


