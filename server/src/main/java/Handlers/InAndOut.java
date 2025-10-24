package Handlers;

import com.google.gson.reflect.TypeToken;
import dataaccess.*;
import io.javalin.*;
import com.google.gson.*;
import io.javalin.http.Context;
import java.lang.reflect.Type;
import java.util.*;

public class InAndOut {

    AuthAccess authData;
    Gson gson = new Gson();

    public InAndOut(AuthAccess authData) {
        this.authData = authData;
    }


    public Map<String, String> requestToJava(Context ctx) {
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        String path = ctx.path();
        Map<String, String> request = gson.fromJson(ctx.body(), type);
        String authToken = ctx.header("authorization");
        if (!path.equals("/db") && !path.equals("/user")) {
            authenticate(authToken, ctx);
        }
        return request;
    }


    public Context responseToHTTP(Map<String, String> response, Context ctx) {
        if(response.containsKey("error")) {
            int errorCode = Integer.parseInt(response.get("error"));
            ctx.status(errorCode).json(Map.of("error", response.get("message")));
        }
        else {
            ctx.status(200);
            if (response.size() == 2) {
                ctx.json(Map.of("status", response.get("message")));
            }
            else {
                String json = gson.toJson(response);
                ctx.result(json);
            }
        }
        return ctx;
    }


    public Context getToHTTP(Map<Integer, List<String>> result, Context ctx) {
        ctx.status(200).json(Map.of("status", "Success"));
        String json = gson.toJson(result);
        ctx.result(json);
        return ctx;
    }


    private void authenticate(String authToken, Context ctx) {
        Map<String, String> error = new HashMap<>();
        try {
            String userToken = authData.getAuthToken(authToken);
            if(authToken == null) {
                error.put("error", "401");
                error.put("message", "Unauthorized user");
                responseToHTTP(error, ctx);
                return;
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
