package dataaccess;

import java.util.*;

public class DataAccess implements UserAccess, GameAccess, AuthAccess{
    Map<String, List<String>> userData = new HashMap<>();
    Map<String, String> authData = new HashMap<>();
    Map<String, List<String>> gameData = new HashMap<>();


    @Override
    public boolean registerUser(String username, String password, String email) throws DataAccessException {
        if (!userData.containsKey(username)) {
            List<String> userInfo = new ArrayList<>(Arrays.asList(password, email));
            userData.put(username, userInfo);
            return true;
        }
        else {
            throw new DataAccessException("User already in system");
            // Code 403
        }
    }


    @Override
    public List<String> getUserData(String username) throws DataAccessException {
        if (userData.containsKey(username)) {
            return userData.get(username);
        }
        else {
            throw new DataAccessException("User not found");
            // code 500
        }
    }


    @Override
    public boolean addAuthToken(String username, String authToken) throws DataAccessException {
        if (!authData.containsKey(username)) {
            authData.put(username, authToken);
            return true;
        }
        else {
            throw new DataAccessException("AuthToken already in use");
        }
        // code 403
    }

    @Override
    public String getAuthToken(String username) throws DataAccessException {
        if (authData.containsKey(username)) {
            return authData.get(username);
        }
        else {
            throw new DataAccessException("Unauthorized User");
            // Code 401
        }
    }
}