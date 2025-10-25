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
        int id = nextId.getAndIncrement();
        Map<String, Object> game = new HashMap<>(Map.of("gameID", id, "whiteUsername", "", "blackUsername", "", "gameName", gameName));
        gameData.addGame(game);
        result.put("status", "200");
        return result;
    }


    public Map<String, String> joinGame(GameAccess gameData, AuthAccess authData, String authToken, String color, int gameId) throws DataAccessException {
        Map<String, String> result = new HashMap<>();
        String username = authData.getUsername(authToken);
        Map<String, Object> game = gameData.getGame(gameId);
        if (color.equals("WHITE")) {
            color = "whiteUsername";
        }
        else {
            color = "blackUsername";
        }
        if (!game.get(color).equals("")) {
            result.put("error", "403");
            result.put("message", "Color is already taken");
        }
        else {
            gameData.joinGame(username, color, gameId);
            result.put("status", "200");
        }
        return result;
    }
}
