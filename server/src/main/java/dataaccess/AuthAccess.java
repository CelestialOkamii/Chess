package dataaccess;

public interface AuthAccess {

    void clearAuths();
    boolean addAuthToken(String username, String authToken) throws InputException;
    String getUsername(String authToken) throws InputException;
    boolean removeAuthToken(String authToken) throws InputException;
}
