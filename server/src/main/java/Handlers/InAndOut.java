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
        if (!path.equals("/db") && !path.equals("/user") && !(path.equals("/session") && ctx.method().equals("POST"))) {
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
            response.remove("status");
            if (!response.isEmpty()) {
                String json = gson.toJson(response);
                ctx.result(json);
            }
        }
        return ctx;
    }


    public Context getToHTTP(List<Map<String, Object>> result, Context ctx) {
        Map<String, List<Map<String, Object>>> games = new HashMap<>(Map.of("games", result));
        ctx.status(200);
        String json = gson.toJson(games);
        ctx.result(json);
        return ctx;
    }


    private void authenticate(String authToken, Context ctx) {
        Map<String, String> error = new HashMap<>();
        try {
            String userToken = authData.getUsername(authToken);
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
