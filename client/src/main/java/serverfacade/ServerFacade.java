package serverfacade;

import com.google.gson.Gson;
import com.sun.net.httpserver.Request;
import model.AuthData;
import model.UserData;
import request.*;
import result.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ServerFacade {
    private final String serverUrl;
    public ServerFacade(String serverUrl) {
        this.serverUrl = serverUrl;
    }
    public void run(){

    }
    public LoginResult login(LoginRequest request) throws Exception {
        return makeRequest("POST", "/session", request, LoginResult.class);
    }
    public RegisterResult register(RegisterRequest request) throws Exception {
        return makeRequest("POST", "/user", request, RegisterResult.class);
    }
    public LogoutResult logout(LogoutRequest request) throws Exception {
        return makeRequest("DELETE", "/session", request, LogoutResult.class);
    }
    public CreateGameResult createGame(CreateGameRequest request) throws Exception {
        return makeRequest("POST", "/game", request, CreateGameResult.class);
    }
    public ListGamesResult listGames(ListGamesRequest request) throws Exception {
        return makeRequest("GET", "/game", request, ListGamesResult.class);
    }
    public JoinGameResult joinGame(JoinGameRequest request) throws Exception {
        return makeRequest("PUT", "/game", request, JoinGameResult.class);
    }
    public ClearResult clear(ClearRequest request) throws Exception {
        return makeRequest("DELETE", "/db", request, ClearResult.class);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> resultClass) throws Exception {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setReadTimeout(5000);
            http.setRequestMethod(method);
            http.setDoOutput(true);
            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, resultClass);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null){
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()){
                reqBody.write(reqData.getBytes());
            }
        }
    }
    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, Exception {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new Exception(String.valueOf(status));
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

}
