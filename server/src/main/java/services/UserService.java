package services;

import dataaccess.*;
import java.util.*;
import java.lang.*;


public class UserService {

    public UserService() {
    }

    public Map<String, String> registerUser(UserAccess userData, AuthAccess authData, List<String> registerInfo) throws DataAccessException {
        Map<String, String> result = new HashMap<>();
        try {
            List<String> userInfo = userData.getUserData(registerInfo.getFirst());
        } catch (DataAccessException e) {
            boolean addUser = userData.addUser(registerInfo.get(0), registerInfo.get(1), registerInfo.get(2));
            String authToken = UUID.randomUUID().toString();
            boolean addResult = authData.addAuthToken(registerInfo.getFirst(), authToken);
            result.put("status", "200");
            result.put("authToken", authToken);
        }
        return result;
    }


    public Map<String, String> loginUser(UserAccess userData, AuthAccess authData, List<String> loginInfo) throws DataAccessException {
        Map<String, String> result = new HashMap<>();
        List<String> userInfo = userData.getUserData(loginInfo.getFirst());
        if(!userInfo.getFirst().equals(loginInfo.get(1))) {
            result.put("error", "401");
            result.put("message", "Incorrect password provided");
            return result;
        }
        String authToken = UUID.randomUUID().toString();
        boolean addResult = authData.addAuthToken(loginInfo.getFirst(), authToken);
        result.put("status", "200");
        result.put("authToken", authToken);
        return result;
    }

    public Map<String, String> logoutUser(AuthAccess authData, String authToken) throws DataAccessException {
        Map<String, String> result = new HashMap<>();
        boolean removed = authData.removeAuthToken(authToken);
        if (removed) {
            result.put("status", "200");
        }
        return result;
    }
}