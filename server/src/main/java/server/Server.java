package server;

import Handlers.UserHandlers;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dataaccess.*;
import io.javalin.*;

import java.lang.reflect.Type;
import java.util.*;


public class Server {

    private final Javalin javalin;
    final UserAccess userData = new DataAccess();
    final AuthAccess  authData = new DataAccess();
    final GameAccess gameData = new DataAccess();

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        javalin.before (context -> {
            boolean authorized;
            String path = context.path();
            if (!context.header("authToken").isEmpty() && !path.equals("/user") && !path.equals("/db")) {
                String authToken = authData.getAuthToken();
            }
        });


        javalin.get("/session", ctx -> {
            new UserHandlers().loginRequest(ctx, userData, authData);
        });



    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
