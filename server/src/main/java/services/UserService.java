package services;

import dataaccess.*;
import java.util.*;
import java.lang.*;


public class UserService {

    public UserService() {
    }

    public Map<String, String> registerUser(UserAccess userData, AuthAccess authData, List<String> registerInfo) throws InputException {
        Map<String, String> result = new HashMap<>();
        try {
            userData.getUserData(registerInfo.getFirst());
            result.put("error", "403");
            result.put("message", "Error: Already taken");
        } catch (InputException e) {
            userData.addUser(registerInfo.get(0), registerInfo.get(1), registerInfo.get(2));
            String authToken = UUID.randomUUID().toString();
            authData.addAuthToken(registerInfo.getFirst(), authToken);
            result.put("status", "200");
            result.put("username", registerInfo.getFirst());
            result.put("authToken", authToken);
        }
        return result;
    }


    public Map<String, String> loginUser(UserAccess userData, AuthAccess authData, List<String> loginInfo) throws InputException {
        Map<String, String> result = new HashMap<>();
        List<String> userInfo = userData.getUserData(loginInfo.getFirst());
        if(!userInfo.get(0).equals(loginInfo.get(1))) {
            result.put("error", "401");
            result.put("message", "Error: Unauthorized");
            return result;
        }
        String authToken = UUID.randomUUID().toString();
        authData.addAuthToken(loginInfo.getFirst(), authToken);
        result.put("status", "200");
        result.put("username", loginInfo.getFirst());
        result.put("authToken", authToken);
        return result;
    }

    public Map<String, String> logoutUser(AuthAccess authData, String authToken) throws InputException {
        Map<String, String> result = new HashMap<>();
        boolean removed = authData.removeAuthToken(authToken);
        if (removed) {
            result.put("status", "200");
        }
        return result;
    }
}