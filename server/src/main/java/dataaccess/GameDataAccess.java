package dataaccess;

import chess.*;

import com.google.gson.Gson;

import java.sql.*;
import java.util.*;

public class GameDataAccess implements GameAccess{

    @Override
    public void clearGames() throws InputException {
        String sql = "TRUNCATE TABLE auth_data";
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
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
                details.put("game", fromJson(rs.getString("game")));
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
            id = stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error");
        }
        return String.valueOf(id);
    }

    @Override
    public boolean joinGame(String username, String color, int gameID) throws InputException {
        String team = "white_username";
        if (!color.equals("WHITE")) {
            team = "black_username";
        }
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("UPDATE game_data SET " + team + " = ? WHERE game_id = ?");
            statement.setString(1, username);
            statement.setString(2, String.valueOf(gameID));
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error in getUserData");
        }
        return true;
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
                gameData.put(String.valueOf(gameId), String.valueOf(gameId));
                gameData.put("whiteUsername", rs.getString("white_username"));
                gameData.put("blackUsername", rs.getString("black_username"));
                gameData.put("gameName", rs.getString("game_name"));
                gameData.put("game", fromJson(rs.getString("game")));
            }
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error in getUserData");
        }
        return gameData;
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
