package dataaccess;

import dataaccess.*;
import io.javalin.http.Context;
import services.GameService;
import java.util.*;

public interface GameAccess {

    List<Map<String, Object>> getGameList();
    void clearGames() throws InputException;
    boolean addGame(Map<String, Object> game);
    boolean joinGame(String username, String color, int gameID);
    Map<String, Object> getGame(int gameId) throws InputException;
}
