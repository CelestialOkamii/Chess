package services;

import dataaccess.*;
import java.util.*;
import java.lang.*;


public class Get {

    public Get() {
    }

    public List<String> login(UserAccess userData, AuthAccess authData, List<String> loginInfo) throws DataAccessException {
        List<String> result = new ArrayList<>();
        if (loginInfo.size() != 2) {
            result.add("400");
            result.add("One or more fields left empty");
            return result;
        }
        List<String> userInfo = userData.getUserData(loginInfo.getFirst());
        if(!userInfo.getFirst().equals(loginInfo.get(1))) {
            result.add("401");
            result.add("Incorrect password provided");
            return result;
        }
        String authToken = UUID.randomUUID().toString();
        boolean addResult = authData.addAuthToken(loginInfo.getFirst(), authToken);
        result.add("200");
        return result;
    }
}
