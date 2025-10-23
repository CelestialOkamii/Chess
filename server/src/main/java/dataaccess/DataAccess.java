package dataaccess;

import java.util.*;

public class DataAccess implements UserAccess, GameAccess, AuthAccess{
    Map<String, List<String>> userData = new HashMap<>();
    Map<String, List<String>> authData = new HashMap<>();
    Map<String, List<String>> gameData = new HashMap<>();


    @Override
    public boolean registerUser(String username, String password, String email) {
        List<String> userInfo = new ArrayList<>(Arrays.asList(password, email));
        userData.put(username, userInfo);
        return true;
    }


    @Override
    public List<String> getUserData(String username) throws DataAccessException {
        if (userData.containsKey(username)) {
            return userData.get(username);
        }
        else {
            throw new DataAccessException("User not found");
        }
    }
}
