package server;

import dataaccess.*;
import handlers.*;
import io.javalin.*;

public class Server {


    private final Javalin javalin;
    final DataAccess dataAccess;
    final UserAccess userData;
    final AuthAccess  authData;
    final GameAccess gameData;
    InAndOut inAndOut;
    Clear clear;
    User user;
    Game game;


    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        this.dataAccess = new DataAccess();
        this.userData = dataAccess;
        this.authData = dataAccess;
        this.gameData = dataAccess;
        this.inAndOut = new InAndOut(authData);
        this.clear = new Clear(inAndOut, authData, userData, gameData);
        this.user = new User(userData, authData, inAndOut);
        this.game = new Game(authData, gameData, inAndOut);


        // Register your endpoints and exception handlers here.
        javalin.delete("/db", ctx -> {
            clear.clearDB(ctx);
        });


        javalin.post("/user", ctx -> {
            user.register(ctx);
        });


        javalin.post("/session", ctx -> {
            user.login(ctx);
        });


        javalin.delete("/session", ctx -> {
            user.logout(ctx);
        });


        javalin.get("/game", ctx -> {
            game.gameList(ctx);
        });


        javalin.post("/game", ctx -> {
            game.addGame(ctx);
        });


        javalin.put("/game", ctx -> {
            game.joinGame(ctx);
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
