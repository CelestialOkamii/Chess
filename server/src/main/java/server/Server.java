package server;

import handlers.*;
import dataaccess.*;
import io.javalin.*;


public class Server {

    private final Javalin javalin;
    final DataAccess dataAccess = new DataAccess();
    final UserAccess userData = dataAccess;
    final AuthAccess  authData = dataAccess;
    final GameAccess gameData = dataAccess;
    InAndOut inAndOut = new InAndOut(authData);
    Clear clear = new Clear(inAndOut, authData, userData, gameData);
    User user = new User(userData, authData, inAndOut);
    Game game = new Game(authData, gameData, inAndOut);

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

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
