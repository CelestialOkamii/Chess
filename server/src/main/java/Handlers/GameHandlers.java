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
        List<Map<String, Object>> result = gameService.getGameList(gameData);
        return inAndOut.getToHTTP(result, ctx);
    }

    public Context addGame(Context ctx) {
        Map<String, String> result = new HashMap<>();
        Map<String, String> request = inAndOut.requestToJava(ctx);
        if (!request.containsKey("gameName")) {
            result.put("error", "400");
            result.put("message", "One or more fields left empty");
        }
        else {
            result = gameService.addGame(gameData, request.get("gameName"));
        }
        return inAndOut.responseToHTTP(result, ctx);
    }
}
