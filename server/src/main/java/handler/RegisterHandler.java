package handler;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import request.RegisterRequest;
import result.RegisterResult;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class RegisterHandler {
    private final RegisterService registerService;

    public RegisterHandler(RegisterService registerService) {
        this.registerService = registerService;
    }
    public Object register(Request req, Response res){
        try {
            RegisterRequest registerRequest = new Gson().fromJson(req.body(), RegisterRequest.class);
            RegisterResult registerResult = registerService.register(registerRequest.getUsername(),
                    registerRequest.getPassword(),
                    registerRequest.getEmail());
            if (registerResult.getErrorMessage() == null){
                return new Gson().toJson(registerResult);
            } else {
                res.status(getStatusCode(registerResult.getErrorMessage()));
                return new Gson().toJson(registerResult);
            }
        } catch (DataAccessException e) {
            res.status(500);
            return new Gson().toJson(new ErrorMessage("Internal server error: " + e.getMessage()));
        }

    }
    private int getStatusCode(String errorMsg){
        if (errorMsg.contains("already taken")){
            return 403;
        } else {
            return 400;
        }
    }

}
