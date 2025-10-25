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
        inAndOut.authenticate(ctx.header("authorization"), ctx);
        List<Map<String, Object>> result = gameService.getGameList(gameData);
        return inAndOut.getToHTTP(result, ctx);
    }

    public Context addGame(Context ctx) {
        Map<String, String> result = new HashMap<>();
        Map<String, String> request = inAndOut.requestToJava(ctx);
        inAndOut.authenticate(ctx.header("authorization"), ctx);
        if (!request.containsKey("gameName")) {
            result.put("error", "400");
            result.put("message", "One or more fields left empty");
        }
        else {
            result = gameService.addGame(gameData, request.get("gameName"));
        }
        return inAndOut.responseToHTTP(result, ctx);
    }


    public Context joinGame(Context ctx) throws DataAccessException {
        Map<String, String> result = new HashMap<>();
        Map<String, String> request = inAndOut.requestToJava(ctx);
        inAndOut.authenticate(ctx.header("authorization"), ctx);
        if (!request.containsKey("playerColor") || !request.containsKey("gameID")) {
            result.put("error", "400");
            result.put("message", "One or more fields left empty");
        }
        else {
            result = gameService.joinGame(gameData, authData, ctx.header("authorization"), request.get("playerColor"), Integer.parseInt(request.get("gameID")));
        }
        return inAndOut.responseToHTTP(result, ctx);
    }
}
