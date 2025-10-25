package Handlers;

import dataaccess.*;
import java.util.*;
import io.javalin.*;
import io.javalin.http.Context;
import services.ClearService;

public class ClearHandler {

    InAndOut inAndOut;
    AuthAccess authData;
    UserAccess userData;
    GameAccess gameData;
    ClearService clearService;


    public ClearHandler(InAndOut inAndOut, AuthAccess authData, UserAccess userData, GameAccess gameData) {
        this.inAndOut = inAndOut;
        this.authData = authData;
        this.userData = userData;
        this.gameData = gameData;
        this.clearService = new ClearService();
    }

    public Context clearDB(Context ctx) {
        Map<String, String> result = clearService.clearDB(authData, userData, gameData);
        return inAndOut.responseToHTTP(result, ctx);
    }
}
