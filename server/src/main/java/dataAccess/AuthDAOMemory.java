package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashSet;
import java.util.UUID;

public class AuthDAOMemory implements AuthDAO{
    private HashSet<AuthData> db;
    AuthDAOMemory(){
        db = new HashSet<>();
    }
    @Override
    public void createAuth(String username){
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(username, authToken);
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
                break; // Found the auth data, no need to continue searching
            }
        }
        if (authToRemove != null) {
            db.remove(authToRemove);
        }
    }
    @Override
    public void clear(){
        db.clear();
    }
}
