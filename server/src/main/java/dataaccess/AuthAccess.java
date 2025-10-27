package dataaccess;

import java.util.*;

public interface AuthAccess {

    void clearAuths();
    Map<String, String> getAuthMap();
    boolean addAuthToken(String username, String authToken) throws InputException;
    String getUsername(String authToken) throws InputException;
    boolean removeAuthToken(String authToken) throws InputException;
}
