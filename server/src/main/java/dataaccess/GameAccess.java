package dataaccess;

import java.util.*;

public interface GameAccess {

    void clearGames();
    List<Map<String, Object>> getGameList();
    boolean addGame(Map<String, Object> game);
    boolean joinGame(String username, String color, int gameID);
    Map<String, Object> getGame(int gameId) throws InputException;
}
