package handler;

import com.google.gson.Gson;
import dataAccess.Exceptions.AlreadyTakenException;
import dataAccess.Exceptions.BadRequestException;
import dataAccess.Exceptions.DataAccessException;
import request.RegisterRequest;
import result.RegisterResult;
import service.RegisterService;
import spark.Request;
import spark.Response;

import java.util.Objects;

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
            return new Gson().toJson(registerResult);
        } catch (BadRequestException e) {
            res.status(400);
            return new Gson().toJson(new ErrorMessage("Error: bad request"));
        } catch (AlreadyTakenException e){
            res.status(403);
            return new Gson().toJson(new ErrorMessage("Error: user already taken"));
        } catch (DataAccessException e) { // why does it make me add this last catch??
            res.status(500);
            return new Gson().toJson(new ErrorMessage("Error: " + e.getMessage()));
        }
    }
}

