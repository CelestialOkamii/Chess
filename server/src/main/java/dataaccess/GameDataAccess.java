package dataaccess;

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
    public boolean addGame(Map<String, Object> game) {
        return false;
    }

    @Override
    public boolean joinGame(String username, String color, int gameID) {
        return false;
    }

    @Override
    public Map<String, Object> getGame(int gameId) throws InputException {
        return Map.of();
    }
}
