package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GameDatabaseAccess implements GameAccess{
    @Override
    public void clearGames() throws InputException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("TRUNCATE TABLE `game_data`");
            statement.executeUpdate();
        } catch (DataAccessException | SQLException ex) {
            throw new InputException("500", "Error: Internal error");
        }
    }

    @Override
    public List<Map<String, Object>> getGameList() {

    }

    @Override
    public boolean addGame(Map<String, Object> game) {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM users WHERE username = " + username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                throw new InputException("403", "Error: Already taken");
            }
            else {
                String addStatement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(addStatement);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, email);
                stmt.executeUpdate();
            }
        } catch (DataAccessException | SQLException ex) {
            throw new InputException("500", "Error: Internal error");
        }
        return true;
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
