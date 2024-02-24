package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashSet;
import java.util.UUID;

public class AuthDAOMemory implements AuthDAO{
    //do the dao classes need to handle the fail cases??
    private HashSet<AuthData> db;
    public AuthDAOMemory(){
        db = new HashSet<>();
    }
    @Override
    public void createAuth(String username){
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(username, authToken);
//        if (db.contains(authData.authToken())){
//            throw new DataAccessException("Added auth that already exists: " + );
//        }
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
    public void clear(){
        db.clear();
    }
}
