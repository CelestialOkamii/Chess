package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
        return List.of();
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
