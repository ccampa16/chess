package clientTests;

import dataAccess.Exceptions.DataAccessException;
import model.AuthData;
import org.junit.jupiter.api.*;
import request.ClearRequest;
import request.LoginRequest;
import request.RegisterRequest;
import result.ClearResult;
import result.LoginResult;
import result.RegisterResult;
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
        facade = new ServerFacade("http://localhost:" + port);
    }
    @BeforeEach
    public void clear() {
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
    }
    @Test
    void register() throws Exception {
        RegisterRequest request = new RegisterRequest("user", "pass", "email");
        try {
            RegisterResult result = facade.register(request);
            assertNotNull(result);
            assertNotNull(result.getAuthToken());
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    @Test
    void registerFail() throws Exception {

    }
    @Test
    void logout() throws Exception {

    }
    @Test
    void logoutFail() throws Exception {

    }
    @Test
    void createGame() throws Exception {

    }
    @Test
    void createGameFail() throws Exception {

    }
    @Test
    void listGame() throws Exception {

    }
    @Test
    void listGameFail() throws Exception {

    }
    @Test
    void joinGame() throws Exception {

    }
    @Test
    void joinGameFail() throws Exception {

    }
    @Test
    void observeGame() throws Exception {

    }
    @Test
    void observeGameFail() throws Exception {

    }

}
