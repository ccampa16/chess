package handler;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import request.ClearRequest;
import result.ClearResult;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {
    private final ClearService clearService;

    public ClearHandler(ClearService clearService) {
        this.clearService = clearService;
    }


    public Object clear(Request req, Response res) {
        try {
            clearService.clear();
            return new Gson().toJson(new ClearResult("Database cleared"));
        } catch(DataAccessException e){
            res.status(500);
            return new Gson().toJson(new ErrorMessage("Internal server error:" + e.getMessage()));
        }
    }


}
