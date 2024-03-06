package dataAccess.Memory;

import dataAccess.AuthDAO;
import model.AuthData;

import java.util.HashSet;

public class AuthDAOMemory implements AuthDAO {
    //do the dao classes need to handle the fail cases??
    private static HashSet<AuthData> db;
    public AuthDAOMemory(){
        db = new HashSet<>();
    }
    @Override
    public void createAuth(String username, String authToken){
        AuthData authData = new AuthData(authToken, username);
        db.add(authData);
    }
    @Override
    public AuthData getAuth(String authToken){
        for (AuthData auth : db){
            if (auth.authToken().equals(authToken)){
                return auth;
            }
        }
        return null;
    }
    @Override
    public void deleteAuth(String authToken){
        AuthData authToRemove = null;
        for (AuthData auth : db) {
            if (auth.authToken().equals(authToken)) {
                authToRemove = auth;
                break;
            }
        }
        if (authToRemove != null) {
            db.remove(authToRemove);
        }
    }
    @Override
    public boolean checkAuth(String authtoken){
        return getAuth(authtoken) != null;
    }
    @Override
    public void clear(){
        db.clear();
    }
}
