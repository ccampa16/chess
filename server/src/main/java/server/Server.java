package server;

import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        Spark.notFound("<html><body>My custom 404 page</body></html>");
        // Register your endpoints and handle exceptions here.
        createRoutes();
        Spark.awaitInitialization();
        return Spark.port();
    }
    private static void createRoutes (){
        Spark.before((req, res) -> System.out.println("Executing route: " + req.pathInfo()));
        //Spark.delete("/db", (req, res) -> clear(req, res));
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
    public static void main(String[] args) {
        new Server().run(8080);
    }
}
