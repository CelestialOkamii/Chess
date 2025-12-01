package dataaccess;

import java.util.*;
import java.sql.*;

public class UserDatabaseAccess implements UserAccess{

    public UserDatabaseAccess() {
    }

    @Override
    public void clearUsers() throws InputException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("TRUNCATE TABLE `user_data`");
            statement.executeUpdate();
        } catch (DataAccessException | SQLException ex) {
            throw new InputException("500", "Error: Internal error");
        }
    }

    @Override
    public Map<String, List<String>> getUserMap() throws InputException {
        Map<String, List<String>> result = new HashMap<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM user_data");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                result.put(username, Arrays.asList(password, email));
            }
        } catch (DataAccessException | SQLException ex) {
            throw new InputException("500", "Error: Internal error");
        }
        return result;
    }

    @Override
    public boolean addUser(String username, String password, String email) throws InputException {
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
    public List<String> getUserData(String username) throws InputException {
        List<String> userData = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM users WHERE username = " + username);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                throw new InputException("401", "Error: Unauthorized");
            }
            else {
                userData.add(rs.getString("username"));
                userData.add(rs.getString("password"));
                userData.add(rs.getString("email"));
            }
        } catch (DataAccessException | SQLException ex) {
            throw new InputException("500", "Error: Internal error");
        }
        return userData;
    }
}
