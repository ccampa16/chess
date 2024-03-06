package dataAccess.Memory;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.UserDAO;
import model.UserData;

import java.util.HashSet;

public class UserDAOMemory implements UserDAO {
    private static HashSet<UserData> db;
    public UserDAOMemory(){
        db = new HashSet<>();
    }

    @Override
    public UserData createUser(String username, String password, String email){
        UserData newUser = new UserData(username, password, email);
        db.add(newUser);
        return newUser;
    }
    @Override
    public UserData getUser (String username) throws DataAccessException {
        for (UserData user : db){
            if (user.username().equals(username)){
                return user;
            }
        }
        return null;
    }
    @Override
    public boolean checkUser(String username, String password){
        for (UserData user : db){
            if (user.username().equals(username) && user.password().equals(password)){
                return true;
            }
        }
        return false;
    }
    @Override
    public void clear(){
        db.clear();
    }
}
