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


    public UserHandlers(UserAccess userData, AuthAccess authData, InAndOut inAndOut) {
        this.userData = userData;
        this.authData = authData;
        this.inAndOut = inAndOut;
    }


    public Context register(Context ctx) throws DataAccessException {
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
        return inAndOut.responseToHTTP(result, ctx);
    }


    public Context login(Context ctx) throws DataAccessException {
        Map<String, String> result = new HashMap<>();
        Map<String, String> request = inAndOut.requestToJava(ctx);
        if (request.size() != 2) {
            result.put("error", "400");
            result.put("message", "One or more fields left empty");
        }
        else {
            List<String> loginInfo = new ArrayList<>(Arrays.asList(request.get("username"), request.get("password")));
            result = userService.loginUser(userData, authData, loginInfo);
        }
        return inAndOut.responseToHTTP(result, ctx);
    }


    public Context logout(Context ctx) throws DataAccessException {
        Map<String, String> result = userService.logoutUser(authData, ctx.header("authorization"));
        return inAndOut.responseToHTTP(result, ctx);
    }
}
