package handlers;

import dataaccess.*;
import io.javalin.http.Context;
import services.GameService;
import java.util.*;

public class Game {

    AuthAccess authData;
    GameAccess gameData;
    InAndOut inAndOut;
    GameService gameService;

    public Game(AuthAccess authData, GameAccess gameData, InAndOut inAndOut) {
        this.authData = authData;
        this.gameData = gameData;
        this.inAndOut = inAndOut;
        this.gameService = new GameService();
    }
}
