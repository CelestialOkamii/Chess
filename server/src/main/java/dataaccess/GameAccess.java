package dataaccess;

import dataaccess.*;
import io.javalin.http.Context;
import services.GameService;
import java.util.*;

public interface GameAccess {

    List<Map<String, Object>> getGameList() throws InputException;
    void clearGames() throws InputException;
    String addGame(Map<String, Object> game) throws InputException;
    boolean joinGame(String username, String color, int gameID) throws InputException;
    Map<String, Object> getGame(int gameId) throws InputException;
}
