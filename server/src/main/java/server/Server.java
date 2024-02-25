package server;

import com.google.gson.Gson;
import dataAccess.AuthDAOMemory;
import dataAccess.DataAccessException;
import dataAccess.GameDAOMemory;
import dataAccess.UserDAOMemory;
import handler.ClearHandler;
import handler.LoginHandler;
import handler.LogoutHandler;
import handler.RegisterHandler;
import result.ClearResult;
import service.ClearService;
import service.LoginService;
import service.LogoutService;
import service.RegisterService;
import spark.*;


public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        //Spark.notFound("<html><body>My custom 404 page</body></html>");
        // Register your endpoints and handle exceptions here.
        createRoutes();
        Spark.awaitInitialization();
        return Spark.port();
    }
    private static void createRoutes (){
        Spark.before((req, res) -> System.out.println("Executing route: " + req.pathInfo()));

        //is the pet shop example a way of doing it without handler classes? would that work with having so many
        ClearService clearService = new ClearService(new UserDAOMemory(), new AuthDAOMemory(), new GameDAOMemory());
        ClearHandler clearHandler = new ClearHandler(clearService);
        Spark.delete("/db", (request, response) -> clearHandler.clear(request, response));
        RegisterService registerService = new RegisterService(new UserDAOMemory(), new AuthDAOMemory());
        RegisterHandler registerHandler = new RegisterHandler(registerService);
        Spark.post("/user", (request, response) -> registerHandler.register(request, response));
        LoginService loginService = new LoginService(new UserDAOMemory(), new AuthDAOMemory());
        LoginHandler loginHandler = new LoginHandler(loginService);
        Spark.post("/session", (request, response) -> loginHandler.login(request, response));
        LogoutService logoutService = new LogoutService(new AuthDAOMemory());
        LogoutHandler logoutHandler = new LogoutHandler(logoutService);
        Spark.delete("/session", (request, response) -> logoutHandler.logout(request, response));
//        Spark.get("/game", (request, response) -> listGames(request, response));
//        Spark.post("/game", (request, response) -> createGame(request, response));
//        Spark.put("/game", (request, response) -> joinGame(request, response));
    }


    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
    public static void main(String[] args) {
        new Server().run(8080);
    }
}
