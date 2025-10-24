package services;

import dataaccess.*;
import java.util.*;
import java.lang.*;


public class UserService {

    public UserService() {
    }

    public Map<String, String> login(UserAccess userData, AuthAccess authData, List<String> loginInfo) throws DataAccessException {
        Map<String, String> result = new HashMap<>();
        if (loginInfo.size() != 2) {
            result.put("error", "400");
            result.put("message", "One or more fields left empty");
            return result;
        }
        List<String> userInfo = userData.getUserData(loginInfo.getFirst());
        if(!userInfo.getFirst().equals(loginInfo.get(1))) {
            result.put("error", "401");
            result.put("message", "Incorrect password provided");
            return result;
        }
        String authToken = UUID.randomUUID().toString();
        boolean addResult = authData.addAuthToken(loginInfo.getFirst(), authToken);
        result.put("status", "200");
        result.put("message", "Success");
        result.put("authToken", authToken);
        return result;
    }



}