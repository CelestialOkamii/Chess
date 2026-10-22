package handlers;

import com.google.gson.reflect.TypeToken;
import dataaccess.*;
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
        return gson.fromJson(ctx.body(), type);
    }


    public Context responseToHTTP(Map<String, String> response, Context ctx) {
        if(response.containsKey("error")) {
            int errorCode = Integer.parseInt(response.get("error"));
            String json = gson.toJson(Map.of("message", response.get("message")));
            ctx.status(errorCode).result(json);
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


    public Context authenticate(String authToken, Context ctx) {
        try {
            if(authToken == null) {
                responseToHTTP(makeErrorMessage("401", "Error: Unauthorized"), ctx);
            }
            authData.getUsername(authToken);
        } catch (InputException e) {
            return responseToHTTP(makeErrorMessage(e.getErrorCode(), e.getMessage()), ctx);
        }
        return ctx;
    }


    public Map<String, String> makeErrorMessage(String errorCode, String message) {
        return new HashMap<>(Map.of("error", errorCode, "message", message));
    }
}
