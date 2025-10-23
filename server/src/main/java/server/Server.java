package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.javalin.*;
import services.Get;

import java.lang.reflect.Type;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.before;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        before (context -> {
            boolean authorized;
            String path = context.contextPath();
            if (!context.header("authToken").isEmpty() && !path.equals("/user") && !path.equals("/db")) {
                try {
                    
                }
            }
        });


        javalin.get("/session", ctx -> {
            Get signIn = new Get();
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> loginInfo = gson.fromJson(ctx.body(), type);
            signIn.login(loginInfo);
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
