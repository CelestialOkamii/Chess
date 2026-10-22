package dataaccess;

import java.util.List;
import java.util.Map;

public interface UserAccess {

    void clearUsers() throws InputException;
    boolean addUser(String username, String password, String email) throws InputException;
    List<String> getUserData(String username) throws InputException;
}
