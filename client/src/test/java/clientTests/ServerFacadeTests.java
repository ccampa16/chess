package clientTests;

import dataAccess.Exceptions.DataAccessException;
import model.AuthData;
import org.junit.jupiter.api.*;
import request.ClearRequest;
import request.LoginRequest;
import result.ClearResult;
import result.LoginResult;
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
        facade = new ServerFacade(port);
    }
    @BeforeEach
    public void clear() {
        //clear database
//        ClearRequest request = new ClearRequest();
//        try {
//            ClearResult result = facade.clear(request);
//            assertTrue(result.i);
//        }
    }


    @AfterAll
    static void stopServer() {
        server.stop();
    }
    @Test
    public void loginPass() throws DataAccessException {
        LoginRequest request = new LoginRequest("user", "pass");
        try {
            LoginResult result = facade.login(request);
            assertNotNull(result);
            assertNotNull(result.getAuthToken());
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    @Test
    public void loginFail() throws DataAccessException {
        LoginRequest request = new LoginRequest("invalidUser", "invalidPass");
        try {
            LoginResult result = facade.login(request);
            assertNotNull(result);
            assertNotNull(result.getAuthToken());
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
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
