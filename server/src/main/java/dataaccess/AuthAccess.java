package dataaccess;

public interface AuthAccess {

    boolean addAuthToken(String username, String authToken) throws DataAccessException;
    String getAuthToken(String authToken) throws DataAccessException;
    boolean removeAuthToken(String authToken) throws DataAccessException;
}
