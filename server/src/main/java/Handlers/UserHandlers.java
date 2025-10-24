package Handlers;

import dataaccess.*;
import io.javalin.*;
import io.javalin.http.Context;
import services.UserService;

import java.util.*;

public class UserHandlers {
    Context ctx;
    UserAccess userData;
    AuthAccess authData;
    InAndOut inAndOut = new InAndOut(ctx, authData);


    public UserHandlers(Context ctx, UserAccess userData, AuthAccess authData) {
        this.ctx = ctx;
        this.userData = userData;
        this.authData = authData;
    }


    public void loginRequest() throws DataAccessException {
        Map<String, String> request = inAndOut.requestToJava();
        List<String> loginInfo = new ArrayList<>(Arrays.asList(request.get("username"), request.get("password")));
        UserService signIn = new UserService();
        Map<String, String> result = signIn.login(userData, authData, loginInfo);
        inAndOut.responseToHTTP(result);
    }
}
