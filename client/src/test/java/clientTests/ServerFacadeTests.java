package clientTests;

import model.AuthData;
import org.junit.jupiter.api.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;
    //static ServerFacade facade;

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
    void register() throws Exception{
        //AuthData authData = facade.register();
    }

    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

}
