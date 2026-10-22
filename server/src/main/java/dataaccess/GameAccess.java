package dataaccess;

import java.util.*;

public interface GameAccess {

    List<Map<String, Object>> getGameList() throws InputException;
    void clearGames() throws InputException;
}
