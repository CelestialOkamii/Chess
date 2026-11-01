package dataaccess;

import java.util.List;
import java.util.Map;

public class GameDataAccess implements GameAccess{

    @Override
    public void clearGames() throws InputException {

    }

    @Override
    public List<Map<String, Object>> getGameList() throws InputException {
        return List.of();
    }

    @Override
    public boolean addGame(Map<String, Object> game) {
        return false;
    }

    @Override
    public boolean joinGame(String username, String color, int gameID) {
        return false;
    }

    @Override
    public Map<String, Object> getGame(int gameId) throws InputException {
        return Map.of();
    }
}
