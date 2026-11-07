package services;

import dataaccess.*;
import java.util.*;
import java.lang.*;

public class ClearService {

    public ClearService() {
    }

    public Map<String, String> clearDB(AuthAccess authData, UserAccess  userData, GameAccess gameData) throws InputException {
        Map<String, String> result = new HashMap<>();
        gameData.clearGames();
        authData.clearAuths();
        userData.clearUsers();
        result.put("status", "200");
        return result;
    }
}
