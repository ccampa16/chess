package dataAccess;

import model.UserData;
import org.eclipse.jetty.server.Authentication;

import java.util.HashSet;

public class UserDAOMemory implements UserDAO {
    //open connection method
    //close connection method
    //get connection method
    private HashSet<UserData> db;
    UserDAOMemory(){
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
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
    @Override
    public boolean checkUser(String username, String password){
        for (UserData user : db){
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
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
