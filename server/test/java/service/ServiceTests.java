package service;

import dataaccess.*;
import org.junit.jupiter.api.*;
import services.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTests {

    static UserAccess userData = new DataAccess();
    static AuthAccess authData = new DataAccess();
    static GameAccess gameData = new DataAccess();
    ClearService clear = new ClearService();
    static UserService user = new UserService();
    static GameService game = new GameService();
    static String authToken;
    static int gameId;


    // Registers a user, saves their authToken, creates a game, and saves the games gameId
    @BeforeAll
    static void addStartValues() throws InputException {
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


    // Register: Checks that registerUser() will throw a Input Exception when someone tries to register with a username that has already been registered
    @Test
    void failsUsedUsername() throws InputException {
        List<String> registerInfo = new ArrayList<>(Arrays.asList("BeeMovieBabe", "#KenWasGaslit", "BarryBBenson@honeyhive.edu"));
        Map<String, String> result = user.registerUser(userData, authData, registerInfo);
        Map<String, String> expected = new HashMap<>(Map.of("error", "403", "message", "Error: Already taken"));
        assertEquals(expected, result, "Result did not match expected output");
    }


    // Login: Checks that login result is not empty
    @Test
    void resultNotEmpty() throws InputException {
        List<String> loginInfo = new ArrayList<>(Arrays.asList("BeeMovieBabe", "#KenWasGaslit"));
        Map<String, String> result = user.loginUser(userData, authData, loginInfo);
        assertFalse(result.isEmpty());
    }


    // Login: Ensures that program fails when an incorrect password is given
    @Test
    void wrongPassword() throws InputException {
        List<String> registerInfo = new ArrayList<>(Arrays.asList("Silhouettes", "Artist:TheRays", "TwoSilhouettes@OnTheShade.click"));
        Map<String, String> loginInfo = user.registerUser(userData, authData, registerInfo);
        String authToken = loginInfo.get("authToken");
        user.logoutUser(authData, authToken);
        List<String> badInfo = new ArrayList<>(Arrays.asList("Silhouettes", "Artist:Avicii"));
        Map<String, String> result = user.loginUser(userData, authData, badInfo);
        Map<String, String> expected = new HashMap<>(Map.of("error", "401", "message", "Error: Unauthorized"));
        assertEquals(expected, result, "Result did not match expected output");
    }


    // Logout: Ensures that a users authToken is removed when they log out
    @Test
    void removeAuthToken() throws InputException {
        user.logoutUser(authData, authToken);
        assertTrue(authData.getAuthMap().isEmpty());
    }


    // Logout: Ensures that incorrect authTokens can't be used to log a user out
    @Test
    void wrongAuthToken() throws InputException {
        assertThrows(InputException.class, () -> user.logoutUser(authData, "TotallyNotRandomAuthToken"));
    }


    // List Games: Checks that the correct number of games are listed after adding a new game
    @Test
    void lenList() {
        assertEquals(1, game.getGameList(gameData).size());
        game.addGame(gameData, "MelonLord");
        assertEquals(2, game.getGameList(gameData).size());
    }


    // List Games: Checks that getGameList() will throw a NullPointerException when null is passed in
    @Test
    void badType() {
        assertThrows(NullPointerException.class, () -> game.getGameList(null));
    }


    // Create Game: Checks that gameID is returned
    @Test
    void hasGameId() {
        assertNotNull(game.addGame(gameData, "JGWentworth877CASHNOW").get("gameID"));
    }


    // Create Game: Checks that a null game name will not be accepted
    @Test
    void nullName() {
        Map<String, String> expected = new HashMap<>(Map.of("error", "400", "message", "Error: Bad Request"));
        Map<String, String> result = game.addGame(gameData, null);
        assertEquals(expected, result, "Result did not match expected output");
    }


    // Join Game: Checks that joining a game does not make a new game
    @Test
    void noNewGame() throws InputException {
        int numGames = game.getGameList(gameData).size();
        game.joinGame(gameData, authData, authToken, "WHITE", gameId);
        int newNumGames = game.getGameList(gameData).size();
        assertEquals(numGames, newNumGames, "More games than was expected");
    }


    // Join Game: Checks that join will not accept non-uppercase white or black as colors
    @Test
    void wrongColor() throws InputException {
        Map<String, String> expected = new HashMap<>(Map.of("error", "400", "message", "Error: Bad request"));
        Map<String, String> whiteResult = game.joinGame(gameData, authData, authToken, "white", gameId);
        Map<String, String> blackResult = game.joinGame(gameData, authData, authToken, "black", gameId);
        assertEquals(expected, whiteResult, "Result did not match expected output");
        assertEquals(expected, blackResult, "Result did not match expected output");
    }
}
