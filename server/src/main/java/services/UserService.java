package services;

import dataaccess.*;
import java.util.*;
import java.lang.*;

public class UserService {

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
}
