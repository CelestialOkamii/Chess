package server;

import handlers.*;
import dataaccess.*;
import io.javalin.*;


public class Server {

    private final Javalin javalin;
    final DatabaseManager dataAccess;
    final UserAccess userData;
    final AuthAccess  authData;
    final GameAccess gameData;
    InAndOut inAndOut;
    Clear clear;
    User user;
    Game game;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        try {
            this.dataAccess = new DatabaseManager();
        } catch (DataAccessException ex) {
            throw new RuntimeException("Failed to initialize database: " + ex.getMessage());
        }
        this.userData = dataAccess.getUserData();
        this.authData = dataAccess.getAuthData();
        this.gameData = dataAccess.getGameData();
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
