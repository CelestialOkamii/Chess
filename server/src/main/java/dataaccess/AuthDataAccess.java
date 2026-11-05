package dataaccess;

import java.sql.*;
import dataaccess.DatabaseManager;
import java.util.Map;

public class AuthDataAccess implements AuthAccess{

    @Override
    public void clearAuths() throws InputException {
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
    public Map<String, String> getAuthMap() {
        return Map.of();
    }

    @Override
    public boolean addAuthToken(String username, String authToken) throws InputException {
        return false;
    }

    @Override
    public String getUsername(String authToken) throws InputException {
        return "";
    }

    @Override
    public boolean removeAuthToken(String authToken) throws InputException {
        return false;
    }
}
