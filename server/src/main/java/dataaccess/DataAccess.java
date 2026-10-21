package dataaccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
