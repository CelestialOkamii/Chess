package Handlers;

import com.google.gson.Gson;
import dataaccess.AuthAccess;
import dataaccess.UserAccess;
import io.javalin.*;
import io.javalin.http.Context;
import services.UserService;

import java.util.List;

public class UserHandlers {
    Gson gson = new Gson();

    public UserHandlers() {
    }


    public Context loginRequest(Context ctx, UserAccess userData, AuthAccess authData) {
        UserService signIn = new UserService();
        List<String> result = signIn.login(userData, authData, loginInfo);
    }
}
