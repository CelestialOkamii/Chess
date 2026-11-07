package dataaccess;

import chess.*;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GameDataAccess implements GameAccess{

    @Override
    public void clearGames() throws InputException {
        String sql = "TRUNCATE TABLE auth_data";
        Connection conn = DatabaseManager.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error");
        }
    }

    @Override
    public List<Map<String, Object>> getGameList() throws InputException {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM game_data");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Map<String, Object> details = new HashMap<>();
                details.put("gameId", rs.getInt("game_id"));
                details.put("whiteUsername", rs.getString("white_username"));
                details.put("blackUsername", rs.getString("black_username"));
                details.put("gameName", rs.getString("game_name"));
                details.put("game", rs.getString("game"));
                result.add(details);
            }
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error");
        }
        return result;
    }

    @Override
    public String addGame(Map<String, Object> game) throws InputException{
        int id = -1;
        try {
            Connection conn = DatabaseManager.getConnection();
            String addStatement = "INSERT INTO game_data (white_username, black_username, game_name, game) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(addStatement, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, String.valueOf(game.get("whiteUsername")));
            stmt.setString(2, String.valueOf(game.get("blackUsername")));
            stmt.setString(3, String.valueOf(game.get("gameName")));
            stmt.setString(4, toJson(new ChessGame()));
            id = stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error");
        }
        return String.valueOf(id);
    }

    @Override
    public boolean joinGame(String username, String color, int gameID) throws InputException {
        return false;
    }

    @Override
    public Map<String, Object> getGame(int gameId) throws InputException {
        Map<String, Object> gameData = new HashMap<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM user_data WHERE game_id = " + gameId);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                throw new InputException("401", "Error: Unauthorized");
            }
            else {
                gameData.put(, rs.getString("username"));
                gameData.put(, rs.getString("password"));
                gameData.put(, rs.getString("email"));
            }
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error in getUserData");
        }
        return userData;
    }


    private String toJson(ChessGame game) {
        Gson gson = new Gson();
        return gson.toJson(game);
    }


    private ChessGame fromJson(String game) {
        Gson gson = new Gson();
        return gson.fromJson(game, ChessGame.class);
    }
}
