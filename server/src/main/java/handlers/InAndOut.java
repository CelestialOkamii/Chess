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
}
