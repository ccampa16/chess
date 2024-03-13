package clientTests;

import model.AuthData;
import org.junit.jupiter.api.*;
import server.Server;
import serverfacade.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        //facade = new ServerFacade(port);
    }
    @BeforeEach
    public void clear(){
        //clear database
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    void register() throws Exception {
        //var authData = facade.register("player1", "password", "p1@email.com");
        //assertTrue(authData.getAuthToken().length() > 10);
    }

    @Test
    public void sampleTest() {
        assertTrue(true);
    }

}
