package dataaccess;

import java.util.*;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class UserDataAccess implements UserAccess{

    public UserDataAccess() {
    }

    @Override
    public void clearUsers() throws InputException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("DELETE FROM user_data");
            statement.executeUpdate();
        } catch (SQLException ex) {
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
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error");
        }
        return result;
    }

    //add password encryption
    @Override
    public boolean addUser(String username, String password, String email) throws InputException, InputException {
        String hashedPwd = encryptPassword(password);
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM user_data WHERE username = ?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                throw new InputException("403", "Error: Already taken");
            }
            else {
                String addStatement = "INSERT INTO user_data (username, password, email) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(addStatement);
                stmt.setString(1, username);
                stmt.setString(2, hashedPwd);
                stmt.setString(3, email);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error");
        }
        return true;
    }

    @Override
    public List<String> getUserData(String username) throws InputException {
        List<String> userData = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM user_data WHERE username = ?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                throw new InputException("401", "Error: Unauthorized");
            }
            else {
                userData.add(rs.getString("username"));
                userData.add(rs.getString("password"));
                userData.add(rs.getString("email"));
            }
        } catch (SQLException ex) {
            throw new InputException("500", "Error: Internal error in getUserData");
        }
        return userData;
    }


    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
