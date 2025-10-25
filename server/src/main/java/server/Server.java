package server;

import Handlers.*;
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
    InAndOut inAndOut = new InAndOut(authData);
    ClearHandler clearHandler = new ClearHandler(inAndOut, authData, userData, gameData);
    UserHandlers userHandlers = new UserHandlers(userData, authData, inAndOut);
    GameHandlers gameHandlers = new GameHandlers(authData, gameData, inAndOut);

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        javalin.delete("/db", ctx -> {
            clearHandler.clearDB(ctx);
        });


        javalin.post("/user", ctx -> {
            userHandlers.register(ctx);
        });


        javalin.post("/session", ctx -> {
            userHandlers.login(ctx);
        });


        javalin.delete("/session", ctx -> {
            userHandlers.logout(ctx);
        });


        javalin.get("/game", ctx -> {
            gameHandlers.gameList(ctx);
        });


        javalin.post("/game", ctx -> {
           gameHandlers.addGame(ctx);
        });


        javalin.put("/game", ctx -> {
           gameHandlers.joinGame(ctx);
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
