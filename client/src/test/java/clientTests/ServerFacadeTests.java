package clientTests;

import dataAccess.Exceptions.DataAccessException;
import model.AuthData;
import org.junit.jupiter.api.*;
import request.*;
import result.ClearResult;
import result.LoginResult;
import result.RegisterResult;
import result.*;
import server.Server;
import serverfacade.ServerFacade;
import dataAccess.Exceptions.*;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;
    private String auth;
    private int gameID;


    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }
    @BeforeEach
    public void setUp(){
        try {
            RegisterResult result = facade.register(new RegisterRequest("user", "pass", "email"));
            auth = result.getAuthToken();
            //CreateGameResult createGameResult = facade.createGame(new CreateGameRequest("newGame"));
            //gameID = createGameResult.getGameID();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @AfterEach
    public void clear() {
        try {
            facade.clear(new ClearRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
    public void loginFail() throws Exception {
        LoginRequest request = new LoginRequest("invalidUser", "pass1");
        try {
            Assertions.assertThrows(Exception.class, () -> {
                facade.login(request);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void register() throws Exception {
        RegisterRequest request = new RegisterRequest("newUser", "newPass", "email");
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
        RegisterRequest request = new RegisterRequest("user", "pass", "email");
        assertThrows(Exception.class, ()->{facade.register(request);
        });
    }
    @Test
    void logout() throws Exception {
    }
    @Test
    void logoutFail() throws Exception {
        String invalidAuth = "invalid";
        assertThrows(Exception.class, () -> {
            facade.logout(new LogoutRequest(invalidAuth));
        });
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
