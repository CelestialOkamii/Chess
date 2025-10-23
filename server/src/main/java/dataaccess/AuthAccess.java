package dataaccess;

public interface AuthAccess {

    boolean addAuthToken(String username, String authToken) throws DataAccessException;
    String getAuthToken(String username) throws DataAccessException;
}
