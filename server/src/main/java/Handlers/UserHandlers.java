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


    public Context register(Context ctx) throws InputException {
        Map<String, String> result = new HashMap<>();
        Map<String, String> request = inAndOut.requestToJava(ctx);
        if (request.size() != 3) {
            result = inAndOut.makeErrorMessage("400", "bad request");
        }
        else {
            List<String> userInfo = new ArrayList<>(Arrays.asList(request.get("username"), request.get("password"), request.get("email")));
            try {
                result = userService.registerUser(userData, authData, userInfo);
            } catch (InputException error) {
                result = inAndOut.makeErrorMessage(error.getErrorCode(), error.getMessage());
            }
        }
        return inAndOut.responseToHTTP(result, ctx);
    }


    public Context login(Context ctx) throws InputException {
        Map<String, String> result = new HashMap<>();
        Map<String, String> request = inAndOut.requestToJava(ctx);
        if (!request.containsKey("username") || !request.containsKey("password")) {
            result = inAndOut.makeErrorMessage("400", "bad request");
        }
        else {
            List<String> loginInfo = new ArrayList<>(Arrays.asList(request.get("username"), request.get("password")));
            try {
                result = userService.loginUser(userData, authData, loginInfo);
            } catch (InputException error) {
                result = inAndOut.makeErrorMessage(error.getErrorCode(), error.getMessage());
            }
        }
        return inAndOut.responseToHTTP(result, ctx);
    }


    public Context logout(Context ctx) throws InputException {
        Map<String, String> result = new HashMap<>();
        try {
            result = userService.logoutUser(authData, ctx.header("authorization"));
        } catch (InputException error) {
            result = inAndOut.makeErrorMessage(error.getErrorCode(), error.getErrorCode());
        }
        return inAndOut.responseToHTTP(result, ctx);
    }
}
