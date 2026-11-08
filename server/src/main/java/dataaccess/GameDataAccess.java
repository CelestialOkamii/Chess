package dataaccess;

import chess.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.*;
import java.util.*;

public class GameDataAccess implements GameAccess{

    @Override
    public void clearGames() throws InputException {
        String sql = "TRUNCATE TABLE game_data";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error");
        }
    }

    @Override
    public List<Map<String, Object>> getGameList() throws InputException {
        List<Map<String, Object>> result = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM game_data")) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Map<String, Object> details = new HashMap<>();
                details.put("gameID", rs.getInt("game_id"));
                details.put("whiteUsername", rs.getString("white_username"));
                details.put("blackUsername", rs.getString("black_username"));
                details.put("gameName", rs.getString("game_name"));
                details.put("game", fromJson(rs.getString("game")));
                result.add(details);
            }
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error in getGameList");
        }
        return result;
    }

    @Override
    public String addGame(Map<String, Object> game) throws InputException{
        int id = -1;
        String addStatement = "INSERT INTO game_data (white_username, black_username, game_name, game) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(addStatement, PreparedStatement.RETURN_GENERATED_KEYS)) {
            if (game.get("whiteUsername") == null) {
                stmt.setNull(1, Types.VARCHAR);
            }
            else {
                stmt.setString(1, String.valueOf(game.get("whiteUsername")));
            }
            if (game.get("blackUsername") == null) {
                stmt.setNull(2, Types.VARCHAR);
            }
            else {
                stmt.setString(2, String.valueOf(game.get("blackUsername")));
            }
            stmt.setString(3, String.valueOf(game.get("gameName")));
            stmt.setString(4, toJson(new ChessGame()));
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                id = keys.getInt(1);
            }
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error in addGame");
        }
        if (id == -1) {
            return null;
        }
        return String.valueOf(id);
    }

    @Override
    public boolean joinGame(String username, String color, int gameID) throws InputException {
        String team = "white_username";
        if (!color.equals("whiteUsername")) {
            team = "black_username";
        }
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement("UPDATE game_data SET " + team + " = ? WHERE game_id = ?")) {
            statement.setString(1, username);
            statement.setString(2, String.valueOf(gameID));
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error in joinGame");
        }
        return true;
    }

    @Override
    public Map<String, Object> getGame(int gameID) throws InputException {
        Map<String, Object> gameData = new HashMap<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM game_data WHERE game_id = ?")) {
            statement.setString(1, String.valueOf(gameID));
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                throw new InputException("401", "Error: Unauthorized");
            }
            else {
                gameData.put(String.valueOf(gameID), String.valueOf(gameID));
                gameData.put("whiteUsername", rs.getString("white_username"));
                gameData.put("blackUsername", rs.getString("black_username"));
                gameData.put("gameName", rs.getString("game_name"));
                gameData.put("game", fromJson(rs.getString("game")));
            }
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error in getGame");
        }
        return gameData;
    }


    private String toJson(ChessGame game) {
        Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .create();
        return gson.toJson(game);
    }


    private ChessGame fromJson(String game) {
        Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .create();
        return gson.fromJson(game, ChessGame.class);
    }
}
