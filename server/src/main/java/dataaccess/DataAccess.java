package dataaccess;

import java.util.*;

public class DataAccess implements UserAccess, GameAccess, AuthAccess{
    Map<String, List<String>> userData = new HashMap<>();
    Map<String, String> authData = new HashMap<>();
    List<Map<String, Object>> gameData = new ArrayList<>();


    @Override
    public void clearAuths() {
        authData = new HashMap<>();
    }


    @Override
    public void clearUsers() {
        userData = new HashMap<>();
    }


    @Override
    public void clearGames() {
        gameData = new ArrayList<>();
    }


    @Override
    public Map<String, List<String>> getUserMap() {
        return userData;
    }


    @Override
    public Map<String, String> getAuthMap() {
        return authData;
    }


    @Override
    public boolean addUser(String username, String password, String email) throws InputException {
        if (!userData.containsKey(username)) {
            List<String> userInfo = new ArrayList<>(Arrays.asList(password, email));
            if (username == null) {
                username = "null";
            }
            userData.put(username, userInfo);
            return true;
        }
        else {
            throw new InputException("403", "Error: Already taken");
        }
    }


    @Override
    public List<String> getUserData(String username) throws InputException {
        if (userData.containsKey(username)) {
            return userData.get(username);
        }
        else {
            throw new InputException("401", "Error: Unauthorized");
        }
    }


    @Override
    public boolean addAuthToken(String username, String authToken) throws InputException {
        if (!authData.containsKey(authToken)) {
            authData.put(authToken, username);
            return true;
        }
        else {
            throw new InputException("403", "Error: Already taken");
        }
    }


    // Returns username
    @Override
    public String getUsername(String authToken) throws InputException {
        if (authData.containsKey(authToken)) {
            return authData.get(authToken);
        }
        else {
            throw new InputException("401", "Error: Unauthorized");
        }
    }

    @Override
    public boolean removeAuthToken(String authToken) throws InputException {
        if (authData.containsKey(authToken)) {
            authData.remove(authToken);
            return true;
        }
        else {
            throw new InputException("401", "Error: Unauthorized");
        }
    }

    @Override
    public List<Map<String, Object>> getGameList() {
        return gameData;
    }

    @Override
    public boolean addGame(Map<String, Object> game) {
        gameData.add(game);
        return true;
    }

    @Override
    public boolean joinGame(String username, String color, int gameId) {
        for (Map<String, Object> game : gameData) {
            if ((int) game.get("gameID") == gameId) {
                game.put(color, username);
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> getGame(int gameId) throws InputException {
        for (Map<String, Object> game : gameData) {
            if ((int) game.get("gameID") == gameId) {
                return game;
            }
        }
        throw new InputException("400", "Error: Bad request");
    }
}