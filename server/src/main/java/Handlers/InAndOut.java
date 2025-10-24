package Handlers;

import com.google.gson.reflect.TypeToken;
import dataaccess.*;
import io.javalin.*;
import com.google.gson.*;
import io.javalin.http.Context;
import java.lang.reflect.Type;
import java.util.*;

public class InAndOut {

    Context ctx;
    AuthAccess authData;

    public InAndOut(Context ctx, AuthAccess authData) {
        this.ctx = ctx;
        this.authData = authData;
    }


    public Map<String, String> requestToJava() {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        String path = ctx.path();
        Map<String, String> request = gson.fromJson(ctx.body(), type);
        String authToken = ctx.header("authorization");
        if (!path.equals("/db") && !path.equals("/user")) {
            authenticate(request.get("username"), authToken);
        }
        return request;
    }


    public Context responseToHTTP(Map<String, String> response) {

    }


    private void authenticate(String username, String authToken) {
        Map<String, String> error = new HashMap<>();
        try {
            String userToken = authData.getAuthToken(username);
            if(!authToken.equals(userToken)) {
                error.put("error", "401");
                error.put("message", "Unauthorized user");
                responseToHTTP(error);
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
