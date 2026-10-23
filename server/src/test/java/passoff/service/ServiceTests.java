package passoff.service;

import dataaccess.*;
import org.junit.jupiter.api.*;
import services.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceTests {

    static DataAccess dataAccess = new DataAccess();
    static UserAccess userData = dataAccess;
    static AuthAccess authData = dataAccess;
    static GameAccess gameData = dataAccess;
    static ClearService clear = new ClearService();
    static UserService user = new UserService();
    static GameService game = new GameService();
    static String authToken;
    static int gameId;


    // Registers a user, saves their authToken, creates a game, and saves the games gameId
    @BeforeEach
    void addStartValues() throws InputException {
        clear.clearDB(authData, userData, gameData);
        List<String> registerInfo = new ArrayList<>(Arrays.asList("BeeMovieBabe", "#KenWasGaslit", "BarryBBenson@honeyhive.edu"));
        Map<String, String> result = user.registerUser(userData, authData, registerInfo);
        authToken = result.get("authToken");
        gameId = Integer.parseInt(game.addGame(gameData, "My Cabbages!").get("gameID"));
    }


    // Clear: Checks to see if all data structures in the database are clear
    @Test
    void clearAll() throws InputException {
        addStartValues();
        game.addGame(gameData, "Bingo");
        clear.clearDB(authData, userData, gameData);
        assertTrue(userData.getUserMap().isEmpty());
        assertTrue(authData.getAuthMap().isEmpty());
        assertTrue(gameData.getGameList().isEmpty());
    }


    // Register: Checks to see if the return value of registerUser() is an unempty map and that values are correct
    @Test
    void correctResult() throws InputException {
        clear.clearDB(authData, userData, gameData);
        Map<String, String> expected = new HashMap<>(Map.of("status", "200", "username", "BeeMovieBabe"));
        List<String> registerInfo = new ArrayList<>(Arrays.asList("BeeMovieBabe", "#KenWasGaslit", "BarryBBenson@honeyhive.edu"));
        Map<String, String> result = user.registerUser(userData, authData, registerInfo);
        for (Map.Entry<String, String> entry : authData.getAuthMap().entrySet()) {
            expected.put("authToken", entry.getKey());
        }
        assertEquals(expected, result, "The return value does not match the expected value");
    }


    // Register: Checks that registerUser() will throw a Input Exception when someone
    // tries to register with a username that has already been registered
    @Test
    void failsUsedUsername() throws InputException {
        List<String> registerInfo = new ArrayList<>(Arrays.asList("BeeMovieBabe", "#KenWasGaslit", "BarryBBenson@honeyhive.edu"));
        Map<String, String> result = user.registerUser(userData, authData, registerInfo);
        Map<String, String> expected = new HashMap<>(Map.of("error", "403", "message", "Error: Already taken"));
        assertEquals(expected, result, "Result did not match expected output");
    }
}
