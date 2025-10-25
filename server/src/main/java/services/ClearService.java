package services;

import dataaccess.*;
import java.util.*;
import java.lang.*;

public class ClearService {

    public ClearService() {
    }

    public Map<String, String> clearDB(AuthAccess authData, UserAccess  userData, GameAccess gameData) {
        Map<String, String> result = new HashMap<>();
        authData.clearAuths();
        userData.clearUsers();
        gameData.clearGames();
        result.put("status", "200");
        result.put("message", "Success");
        return result;
    }
}
