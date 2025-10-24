package Handlers;

import dataaccess.*;
import io.javalin.*;
import io.javalin.http.Context;
import services.GameService;

import java.util.*;

public class GameHandlers {

    AuthAccess authData;
    GameAccess gameData;
    InAndOut inAndOut;
    GameService gameService;

    public GameHandlers(AuthAccess authData, GameAccess gameData, InAndOut inAndOut) {
        this.authData = authData;
        this.gameData = gameData;
        this.inAndOut = inAndOut;
        this.gameService = new GameService();
    }


    public Context gameList(Context ctx) {
        inAndOut.requestToJava(ctx);
        Map<Integer, List<String>> result = gameService.getGameList(gameData);
        return inAndOut.getToHTTP(result, ctx);
    }
}
