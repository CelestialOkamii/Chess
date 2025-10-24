package Handlers;

import com.google.gson.reflect.TypeToken;
import io.javalin.*;
import com.google.gson.*;
import io.javalin.http.Context;

import java.lang.reflect.Type;
import java.util.*;

public class InAndOut {

    public InAndOut(Context ctx) {
    }


    public List<String> requestToJava(Context ctx) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>(){}.getType();
        return gson.fromJson(ctx.body(), type);
    }

    public Context responseToHTTP(Context ctx, Map<String, String> response) {

    }
}
