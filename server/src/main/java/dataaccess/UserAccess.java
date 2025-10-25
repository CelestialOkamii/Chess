package dataaccess;

import model.*;
import java.util.*;

public interface UserAccess {

    void clearUsers();
    boolean addUser(String username, String password, String email) throws InputException;
    List<String> getUserData(String username) throws InputException;
}
