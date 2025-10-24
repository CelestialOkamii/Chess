package dataaccess;

import model.*;
import java.util.*;

public interface UserAccess {

    boolean addUser(String username, String password, String email) throws DataAccessException;
    List<String> getUserData(String username) throws DataAccessException;
}
