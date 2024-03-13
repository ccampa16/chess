package serverfacade;

import com.google.gson.Gson;
import com.sun.net.httpserver.Request;
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
    public LoginResult login(LoginRequest request) throws Exception {
        return makeRequest("POST", "/session", request, LoginResult.class);
    }
    private <T> T makeRequest(String method, String path, Object request, Class<T> resultClass) throws Exception {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
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
            //throw new Exception(status, "failure: " + status);
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
