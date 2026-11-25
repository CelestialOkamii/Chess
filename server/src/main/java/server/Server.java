package server;

import dataaccess.*;
import handlers.*;
import io.javalin.Javalin;

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
        try {
            this.dataAccess = new DatabaseManager();
        } catch (InputException ex) {
            throw new RuntimeException("Failed to initialize database: " + ex.getMessage());
        }
        this.userData = dataAccess.getUserData();
        this.authData = dataAccess.getAuthData();
        this.gameData = dataAccess.getGameData();
        this.inAndOut = new InAndOut(authData);
        this.clear = new Clear(inAndOut, authData, userData, gameData);
        this.user = new User(userData, authData, inAndOut);
        this.game = new Game(authData, gameData, inAndOut);
        javalin = Javalin.create(config -> {
            config.staticFiles.add("web");
            config.routes.delete("/db", ctx -> clear.clearDB(ctx));
            config.routes.post("/user", ctx -> user.register(ctx));
            config.routes.post("/session", ctx -> user.login(ctx));
            config.routes.delete("/session", ctx -> user.logout(ctx));
            config.routes.get("/game", ctx -> game.gameList(ctx));
            config.routes.post("/game", ctx -> game.addGame(ctx));
            config.routes.put("/game", ctx -> game.joinGame(ctx));
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
