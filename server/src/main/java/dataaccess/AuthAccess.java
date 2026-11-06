package dataaccess;

import java.util.Map;

public interface AuthAccess {

    Map<String, String> getAuthMap() throws InputException;
    void clearAuths() throws InputException;
    boolean addAuthToken(String username, String authToken) throws InputException;
    String getUsername(String authToken) throws InputException;
    boolean removeAuthToken(String authToken) throws InputException;
}
