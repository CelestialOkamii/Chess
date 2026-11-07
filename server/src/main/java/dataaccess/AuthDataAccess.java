package dataaccess;

import java.sql.*;
import java.util.*;

public class AuthDataAccess implements AuthAccess{

    @Override
    public void clearAuths() throws InputException {
        String sql = "TRUNCATE TABLE auth_data";
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error");
        }
    }

    @Override
    public Map<String, String> getAuthMap() throws InputException{
        Map<String, String> result = new HashMap<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM auth_data");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String auth_token = rs.getString("auth_token");
                String username = rs.getString("username");
                result.put(auth_token, username);
            }
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error");
        }
        return result;
    }

    @Override
    public boolean addAuthToken(String username, String authToken) throws InputException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM auth_data WHERE auth_token = ?");
            statement.setString(1, authToken);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                throw new InputException("403", "Error: Already taken");
            }
            else {
                String addStatement = "INSERT INTO auth_data (auth_token, username) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(addStatement);
                stmt.setString(1, authToken);
                stmt.setString(2, username);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error in addAuthToken");
        }
        return true;
    }

    @Override
    public String getUsername(String authToken) throws InputException {
        String authData = "";
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM auth_data WHERE auth_token = ?");
            statement.setString(1, authToken);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                throw new InputException("401", "Error: Unauthorized");
            }
            else {
                authData = rs.getString("username");
            }
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error");
        }
        return authData;
    }

    @Override
    public boolean removeAuthToken(String authToken) throws InputException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM auth_data WHERE auth_token = ?");
            statement.setString(1, authToken);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                throw new InputException("401", "Error: Unauthorized");
            }
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM auth_data WHERE auth_token = ?");
            stmt.setString(1, authToken);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error");
        }
        return true;
    }
}
