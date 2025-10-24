package dataaccess;

import java.util.*;

public interface GameAccess {

    Map<Integer, List<String>> getGameList();
    boolean addGame(String gameName);
    boolean joinGame(String username, String color, int gameID);
}
