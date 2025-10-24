package services;

import dataaccess.*;
import java.util.*;
import java.lang.*;

public class GameService {

    public GameService() {
    }


    //Collection will have 2 maps. The first will hold the status and message and the second will hold the game list.
    public Map<Integer, List<String>> getGameList(GameAccess gameData) {
        return gameData.getGameList();
    }
}
