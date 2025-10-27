package services;

import dataaccess.*;
import java.util.*;
import java.lang.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GameService {

    private final AtomicInteger nextId = new AtomicInteger(1);

    public GameService() {
    }


    //Collection will have 2 maps. The first will hold the status and message and the second will hold the game list.
    public List<Map<String, Object>> getGameList(GameAccess gameData) {
        return gameData.getGameList();
    }


    public Map<String, String> addGame(GameAccess gameData, String gameName) {
        Map<String, String> result = new HashMap<>();
        if (gameName == null) {
            result.put("error", "400");
            result.put("message", "Error: Bad Request");
        }
        else {
            int id = nextId.getAndIncrement();
            Map<String, Object> game = new HashMap<>(Map.of("gameID", id));
            game.put("whiteUsername", null);
            game.put("blackUsername", null);
            game.put("gameName", gameName);
            gameData.addGame(game);
            result.put("status", "200");
            result.put("gameID", String.valueOf(id));
        }
        return result;
    }


    public Map<String, String> joinGame(GameAccess gameData, AuthAccess authData, String authToken, String color, int gameId) throws InputException {
        Map<String, String> result = new HashMap<>();
        String username = authData.getUsername(authToken);
        Map<String, Object> game = gameData.getGame(gameId);
        if (!color.equals("WHITE") && !color.equals("BLACK")) {
            result.put("error", "400");
            result.put("message", "Error: Bad request");
            return result;
        }
        else if (color.equals("WHITE")) {
            color = "whiteUsername";
        }
        else {
            color = "blackUsername";
        }
        if (game.get(color) != null) {
            result.put("error", "403");
            result.put("message", "Error: Already taken");
        }
        else {
            gameData.joinGame(username, color, gameId);
            result.put("status", "200");
        }
        return result;
    }
}
