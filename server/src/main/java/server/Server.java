package server;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.Memory.AuthDAOMemory;
import dataAccess.Memory.GameDAOMemory;
import dataAccess.Memory.UserDAOMemory;
import dataAccess.SQL.SQLAuthDAO;
import dataAccess.SQL.SQLGameDAO;
import dataAccess.SQL.SQLUserDAO;
import handler.*;
import service.*;
import spark.*;


public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        createRoutes();
        Spark.awaitInitialization();
        return Spark.port();
    }
    private static void createRoutes () {
        Spark.before((req, res) -> System.out.println("Executing route: " + req.pathInfo()));

        //is the pet shop example a way of doing it without handler classes? would that work with having so many
        ClearService clearService = null;
        try {
            clearService = new ClearService(new SQLUserDAO(), new SQLAuthDAO(), new SQLGameDAO());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        ClearHandler clearHandler = new ClearHandler(clearService);
        Spark.delete("/db", (request, response) -> clearHandler.clear(request, response));

        RegisterService registerService = null;
        try {
            registerService = new RegisterService(new SQLUserDAO(), new SQLAuthDAO());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        RegisterHandler registerHandler = new RegisterHandler(registerService);
        Spark.post("/user", (request, response) -> registerHandler.register(request, response));

        LoginService loginService = null;
        try {
            loginService = new LoginService(new SQLUserDAO(), new SQLAuthDAO());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        LoginHandler loginHandler = new LoginHandler(loginService);
        Spark.post("/session", (request, response) -> loginHandler.login(request, response));

        LogoutService logoutService = null;
        try {
            logoutService = new LogoutService(new SQLAuthDAO());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        LogoutHandler logoutHandler = new LogoutHandler(logoutService);
        Spark.delete("/session", (request, response) -> logoutHandler.logout(request, response));

        ListGamesService listGamesService = null;
        try {
            listGamesService = new ListGamesService(new SQLAuthDAO(), new SQLGameDAO());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        ListGamesHandler listGamesHandler = new ListGamesHandler(listGamesService);
        Spark.get("/game", (request, response) -> listGamesHandler.listGames(request, response));

        CreateGameService createGameService = null;
        try {
            createGameService = new CreateGameService(new SQLAuthDAO(), new SQLGameDAO());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        CreateGameHandler createGameHandler = new CreateGameHandler(createGameService);
        Spark.post("/game", (request, response) -> createGameHandler.createGame(request, response));

        JoinGameService joinGameService = null;
        try {
            joinGameService = new JoinGameService(new SQLGameDAO(), new SQLAuthDAO());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        JoinGameHandler joinGameHandler = new JoinGameHandler(joinGameService);
        Spark.put("/game", (request, response) -> joinGameHandler.joinGame(request, response));

    }


    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
    public static void main(String[] args) {
        new Server().run(8080);
    }
}


