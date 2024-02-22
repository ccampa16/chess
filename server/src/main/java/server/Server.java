package server;

import handler.ClearHandler;
import result.ClearResult;
import service.ClearService;
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
//        Spark.delete("/db", (request, response) -> );
//        Spark.post("/user", (request, response) -> register(request, response));
//        Spark.post("/session", (request, response) -> login(request, response));
//        Spark.delete("/session", (request, response) -> logout(request, response));
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
