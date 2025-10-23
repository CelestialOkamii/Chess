package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dataaccess.*;
import io.javalin.*;
import services.Get;

import java.lang.reflect.Type;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.before;

public class Server {

    private final Javalin javalin;
    final UserAccess userData = new DataAccess();
    final AuthAccess  authData = new DataAccess();
    final GameAccess gameData = new DataAccess();

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        before (context -> {
            boolean authorized;
            String path = context.contextPath();
            if (!context.header("authToken").isEmpty() && !path.equals("/user") && !path.equals("/db")) {

            }
        });


        javalin.get("/session", ctx -> {
            Get signIn = new Get();
            Gson gson = new Gson();
            Type type = new TypeToken<List<String>>(){}.getType();
            List<String> loginInfo = gson.fromJson(ctx.body(), type);
            signIn.login(userData, authData, loginInfo);
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
