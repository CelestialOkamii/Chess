package Handlers;

import dataaccess.*;
import io.javalin.*;
import io.javalin.http.Context;
import services.UserService;

import java.util.*;

public class UserHandlers {
    UserAccess userData;
    AuthAccess authData;
    InAndOut inAndOut;
    UserService userService = new UserService();


    public UserHandlers(UserAccess userData, AuthAccess authData) {
        this.userData = userData;
        this.authData = authData;
        this.inAndOut = new InAndOut(authData);
    }


    public void loginRequest(Context ctx) throws DataAccessException {
        Map<String, String> result = new HashMap<>();
        Map<String, String> request = inAndOut.requestToJava(ctx);
        if (request.size() != 2) {
            result.put("error", "400");
            result.put("message", "One or more fields left empty");
        }
        else {
            List<String> loginInfo = new ArrayList<>(Arrays.asList(request.get("username"), request.get("password")));
            result = userService.login(userData, authData, loginInfo);
        }
        inAndOut.responseToHTTP(result, ctx);
    }


    public void registerRequest(Context ctx) {
        Map<String, String> result = new HashMap<>();
        Map<String, String> request = inAndOut.requestToJava(ctx);
        if (request.size() != 3) {
            result.put("error", "400");
            result.put("message", "One or more fields left empty");
        }
        else {
            List<String> userInfo = new ArrayList<>(Arrays.asList(request.get("username"), request.get("password"), request.get("email")));
            result = userService.registerUser(userData, authData, userInfo);
        }
        inAndOut.responseToHTTP(result, ctx);
    }
}
