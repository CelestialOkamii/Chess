package dataaccess;

import java.util.Map;

public class AuthDatabaseAccess implements AuthAccess{
    @Override
    public void clearAuths() {

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
